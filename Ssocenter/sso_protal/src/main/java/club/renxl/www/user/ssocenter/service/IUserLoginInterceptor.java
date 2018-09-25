package club.renxl.www.user.ssocenter.service;

import club.renxl.www.response.BaseResponse;
import club.renxl.www.user.ssocenter.dao.domain.User;

/**
 * <p>用户操作</p>
 * 
 * @author renxl
 * @date 20180910
 * @version 1.0.0
 *
 */
public interface IUserLoginInterceptor {
 
	
	 
	/**
	 *   用户是否登录在线
	 * @param token 
	 * @param loginWay  app|web
	 * @return
	 */
	 BaseResponse isOnline(String token,String loginWay) ;
	
	/**
	 * @param 获取权限信息
	 * @return
	 */
	 BaseResponse getPermissions(User user);
	
	
	
	


}
