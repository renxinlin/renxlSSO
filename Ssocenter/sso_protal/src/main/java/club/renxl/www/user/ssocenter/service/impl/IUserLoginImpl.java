package club.renxl.www.user.ssocenter.service.impl;

import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.support.json.JSONUtils;

import club.renxl.www.common.constants.Separator;
import club.renxl.www.common.utlis.MD5;
import club.renxl.www.enums.SystemLogEntity;
import club.renxl.www.enums.UserResponse;
import club.renxl.www.redis.constants.RedisPatternConstant;
import club.renxl.www.response.BaseResponse;
import club.renxl.www.user.ssocenter.constants.LoginConstant;
import club.renxl.www.user.ssocenter.dao.UserMapper;
import club.renxl.www.user.ssocenter.dao.domain.User;
import club.renxl.www.user.ssocenter.dao.domain.UserExample;
import club.renxl.www.user.ssocenter.dao.domain.UserExample.Criteria;
import club.renxl.www.user.ssocenter.redis.dao.RedisDao;
import club.renxl.www.user.ssocenter.service.IUserLogin;

/**
 * <p>用户操作</p>
 * 
 * @author renxl
 * @date 20180910
 * @version 1.0.0
 *
 */
@Service
public class IUserLoginImpl implements IUserLogin{
	/**
	 * 只有一个元素的集合元素下标
	 */
	private static final int JUST_HAVE_ONE_ELEMENT_INEDX = 0;
	
	private Logger logger = LoggerFactory.getLogger(IUserLoginImpl.class);
	
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	@SuppressWarnings("rawtypes")
	private RedisTemplate redisTemplate;
	
	
	@Autowired
	private UserMapper userMapper;
	
	
	/**
	  *  <p>登录；</p>
	  *  
	  *	缓存足够时支持并发登录
	  *	以及拦截器并发
	  *	非双十一场景只要redis资源配置好基本无问题
	  *	双十一这种场景，可以提前将数据库信息推向redis
	 * @return
	 */
	public BaseResponse login(User user,HttpServletRequest request, HttpServletResponse response) {
	
		
		/*
		 * 	基于监听模式的springcloud config
		 *  动态切换redis
		 *  动态加载数据源
		 * if() {
		 * redisDao = singleton
		 * }else{
		 * redis = clusters
		 * }
		 * 
			
		}*/
		
		
		//获取redis用户/失败获取db数据;userName和userID都是唯一的
		//             Set<String> set = jedis.keys("*phone*"); 
		 
		switch (user.getLoginType()) {
		
		
			case  LoginConstant.WEIBO:
				return BaseResponse.error("暂未开通...");
				
				
 			case  LoginConstant.QQ:
				return BaseResponse.error("暂未开通...");
				
				
			case  LoginConstant.USERNAME:
				// step1:  校验表单
				boolean legal =   validateLoginTypeWithUserName(user);
				if (legal) {
					return BaseResponse.argsError("用户名或密码错误...");
				}
				
				
				// step2:  获取用户信息(先redis后db)并始终尝试缓存
				// redis模板 USERId_USERNAME_PHONE_QQ_WEIXIN_WEBBO_APP_OTHERS;
				User existsUser = 								  getUserInfoFromRedisTemplateKey(user);
				if (existsUser == null) {
					
					// 数据库获取信息并且缓存
					existsUser = getUserInfoFromDB(user);
					if (existsUser == null) {
						return BaseResponse.error(UserResponse.USER_NOT_EXISTS.getCode(), "用户不存在...");
					}else {
						// 存储数据到redis
						setUserInfoToRedisAsTemplateKey(existsUser);
					}
				}
				
				
				// step3 校验密码生成token
				boolean rightPassWord = validatePasswordByUser(user, existsUser);
				if(!rightPassWord) {
					return BaseResponse		      .argsError("用户名或密码错误...");
				}
				
				
				// 生成token，并且存储30分钟(web)/15天(app)的token到cookie,服务端存储到redis
				String token =  UUID.randomUUID()	.toString().replace("-", "");
				boolean operationSuccess							 	 = false;
				if (LoginConstant.APP.equals( user.getLoginWay())) {
					Cookie cookie 												  = new Cookie(LoginConstant.TOKEN,token);
					response																		   .addCookie(cookie);
					operationSuccess = redisDao.set(LoginConstant.APP + token, existsUser.getId(),LoginConstant.APP_TIME);
				
				
				} else if(LoginConstant.WEB.equals( user.getLoginWay())){
					Cookie cookie 																= new Cookie(token,token);
					response																		   .addCookie(cookie);
					operationSuccess = redisDao.set(LoginConstant.WEB + token, existsUser.getId(),LoginConstant.WEB_TIME);
					
					
				}else {
					// 校验参数后走不到这一步
					return BaseResponse.argsError("请谨慎操作,小心被禁...");
				}
				
				
				// 响应客户端
				if(!operationSuccess) {
					return BaseResponse.error(UserResponse.LOGIN_SAVE_ERROR.getCode(), "登录可能出现异常,请重新登录...");
				}
				return BaseResponse																.success("欢迎登录");

				
			case  LoginConstant.PHONE:
				return BaseResponse.error("暂未开通...");
				
				
			case  LoginConstant.WEIXIN:
				return BaseResponse.error("暂未开通...");
				
				
			default:
				return BaseResponse.argsError("请选择正确的登录方式...");
		}
		
	}
	/**
	 * 
	 * @param user 表单参数
	 * @param existsUser 系统数据
	 * @return
	 * @throws Exception 
	 */
	private boolean validatePasswordByUser(User user,User existsUser) {
		try {
			return MD5.verify(user.getPassword(), existsUser.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	/**
	 * 将查询到的用户信息生成模板KEY保存到redis
	 * @param existsUser
	 */
	private boolean setUserInfoToRedisAsTemplateKey(User existsUser) {
		// USERId_USERNAME_PHONE_QQ_WEIXIN_WEBBO_APP_OTHERS;
		String template = "";
		StringBuffer sb = new StringBuffer();
		if(!StringUtils.isEmpty(existsUser.getId())) {
			sb.append(existsUser.getId().toString());
		}
		
		if(!StringUtils.isEmpty(existsUser.getUsername())) {
			sb.append(Separator.COLON).append(existsUser.getUsername());
		}
		
		if(!StringUtils.isEmpty(existsUser.getUserPhone())) {
			sb.append(Separator.COLON).append(existsUser.getUserPhone());
		}
		
		if(!StringUtils.isEmpty(existsUser.getQq())) {
			sb.append(Separator.COLON).append(existsUser.getQq());
		}
		
		if(!StringUtils.isEmpty(existsUser.getWx())) {
			sb.append(Separator.COLON).append(existsUser.getWx());
		}
		template = sb.toString();
		if(StringUtils.isEmpty(template)) {
			return false;
		}
		return redisDao.set(template , existsUser) ;
		
	}
	
	
	/**
	 * 根据用户的不同登录类型查询用户信息
	 * @param user
	 * @return
	 */
	private User getUserInfoFromDB(User user) {
		UserExample example = new UserExample();
		Criteria createCriteria = example.createCriteria();
		
		
		if (LoginConstant.USERNAME.equals(user.getLoginType())) {
			createCriteria.andUsernameEqualTo(user.getUsername());
		}
		
		
		if (LoginConstant.PHONE.equals(user.getLoginType())) {
			createCriteria.andUserPhoneEqualTo(user.getUserPhone());
		}
		
		
		if (LoginConstant.QQ.equals(user.getLoginType())) {
			createCriteria.andQqEqualTo(user.getQq());
		}
		
		
		if (LoginConstant.WEIBO.equals(user.getLoginType())) {
			/*
			 *	暂不支持微博登录
			 *  Thread.currentThread().getStackTrace()[1].getMethodName()
			 *	字符串效率高
			 */
			logger.info(JSONUtils.toJSONString(new SystemLogEntity(this.getClass().getName(),"getUserInfoFromDB",UserResponse.ArgsError.getCode(),UserResponse.ArgsError.getMsg())));
			return null;
		}
		
		
		if (LoginConstant.WEIXIN.equals(user.getLoginType())) {
			createCriteria.andWxEqualTo(user.getWx());
		}
		// 根据不同的唯一性参数查询用户信息
		return userMapper.selectByExample(example).get(JUST_HAVE_ONE_ELEMENT_INEDX);
	}

	@SuppressWarnings("unchecked")
	private User getUserInfoFromRedisTemplateKey(User user) {
		try {
			// key都是唯一键的组合体;只会有一个数据
			Set<User> anUserAsList = redisTemplate.keys(RedisPatternConstant.ALL + user.getUsername() + RedisPatternConstant.ALL);
			User anUser = anUserAsList.iterator().next();
			return anUser;
		} catch (Exception e) {
 			return null;
		}
	}

	 
	
	/**
	 * 用户名登录校验
	 * @param user
	 * @return
	 */
	private boolean validateLoginTypeWithUserName(User user) {
		// 可以手机，qq，微信，支付宝，第三方，用户名，密码校验登录
		
		if (!validateLogin(user)) {
			return false;
		}
		
		if (StringUtils.isEmpty(user.getPassword())) {
			return false;
		}
		
		if (StringUtils.isEmpty(user.getUsername())) {
			return false;
		}
		
		return true;
	}












	/**
	 * 所有登录type的基础校验
	 * @param user
	 * @return
	 */
	private boolean validateLogin(User user) {
		if (StringUtils.isEmpty(user)) {
			return false;
		}
		
		if (StringUtils.isEmpty(user.getLoginWay())) {
			return false;
		}
		
		if (StringUtils.isEmpty(user.getLoginType())) {
			return false;
		}
		return true;
		
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



 
	/**
	 * 退出
	 * @return
	 */
	public BaseResponse loginOut(HttpServletRequest request, HttpServletResponse response,User user) {
		if(StringUtils.isEmpty(user) || StringUtils.isEmpty(user.getLoginWay())) {
			return BaseResponse.error("退出失败...");
		}
		boolean del = false;
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(LoginConstant.TOKEN.equals(cookie.getName())) {
				String value = cookie.getValue();
				redisDao.del(user.getLoginWay()+value);
				del = true;
			}
		}
		if(del) {
			return BaseResponse.success("退出成功...");
		}
		return BaseResponse.error("退出失败...");

	}
		
		
	

	/**
	 * 个人信息修改
	 * @return
	 */
	public BaseResponse modifySelfInfo() {
		return null;
		
	}
	
	
	
	


	/**
	 * 注册
	 * @return
	 */
	public BaseResponse register() {
		// 需要手机验证码
		return null;
		
	}
	
	
	/**
	 * 个人信息补充完善
	 * @return
	 */
	@RequestMapping("additional")
	public BaseResponse additional() {
		return null;
		
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////


}
