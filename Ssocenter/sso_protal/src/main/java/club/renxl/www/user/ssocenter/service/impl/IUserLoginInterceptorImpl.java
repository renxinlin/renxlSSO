package club.renxl.www.user.ssocenter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import club.renxl.www.response.BaseResponse;
import club.renxl.www.user.ssocenter.constants.LoginConstant;
import club.renxl.www.user.ssocenter.dao.domain.User;
import club.renxl.www.user.ssocenter.redis.dao.RedisDao;
import club.renxl.www.user.ssocenter.service.IUserLoginInterceptor;

/**
 * <p>用户登录权限服务</p>
 * 
 * @author renxl
 * @date 20180912
 * @version 1.0.0
 *
 */

@Service
public class IUserLoginInterceptorImpl implements IUserLoginInterceptor{
	  
	private Logger logger = LoggerFactory.getLogger(IUserLoginInterceptorImpl.class);
	
	
	@Autowired
	private RedisDao redisDao;
	
	
	/*
	 * (non-Javadoc)
	 * @see club.renxl.www.user.ssocenter.service.IUserLoginInterceptor#isOnline(java.lang.String, java.lang.String)
	 */
	@Override
	public BaseResponse isOnline(String token,String loginWay) {
		boolean legal = validateLoginWay(loginWay,token);
		if(!legal) {
			logger.debug("json log ==> IUserLoginInterceptorImpl isOnline token can not be get");
			return BaseResponse.error("token can not be get or loginway error...");
		}
		// 判断用户是否在线;Object存储user信息 
		Object object = redisDao.get(loginWay+token);
		if(StringUtils.isEmpty(object)) {
			return BaseResponse.error("暂无登录信息");
		}
		// 刷新登录时间
		if(LoginConstant.APP.equals(loginWay)) {
			redisDao.set(loginWay+token, object, LoginConstant.APP_TIME);
		}else {
			redisDao.set(loginWay+token, object, LoginConstant.WEB_TIME);
		}
		// 返回在线信息
		return BaseResponse.success("user online...");
	}
	
	/**
	 * 校验app或web登录的合法性
	 * @param loginWay
	 * @return
	 */
	private boolean validateLoginWay(String loginWay, String token) {
		if(StringUtils.isEmpty(token)) {
			return false;
		}
		if(LoginConstant.APP.equals(loginWay) || LoginConstant.WEB.equals(loginWay)) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see club.renxl.www.user.ssocenter.service.IUserLoginInterceptor#getPermissions(club.renxl.www.user.ssocenter.dao.domain.User)
	 */
	@Override
	public BaseResponse getPermissions(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	 
}
