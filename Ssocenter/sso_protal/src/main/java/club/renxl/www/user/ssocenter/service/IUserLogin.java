package club.renxl.www.user.ssocenter.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public interface IUserLogin {
 
	
	
	/**
	  *  <p>登录；</p>
	  *  
	  *   @author renxl
	  *   @date 20180911
	  *   @version 1.0.0
	  * 
	 * @return
	 */
	BaseResponse login(User user,HttpServletRequest request, HttpServletResponse response);
  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




	/**
	 * 退出
	 *  
	 */
	BaseResponse loginOut(HttpServletRequest request, HttpServletResponse response,User user);
	
	

	/**
	 * 个人信息修改
	 *  
	 */
	 BaseResponse modifySelfInfo();
	
	
	
	


	/**
	 * 注册
	 * @return
	 */
	 BaseResponse register() ;
	
	/**
	 * 个人信息补充完善
	 * @return
	 */
	 BaseResponse additional();
	
	
	
	


}
