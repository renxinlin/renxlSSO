package club.renxl.www.user.ssocenter.httpapi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.renxl.www.response.BaseResponse;
import club.renxl.www.user.ssocenter.dao.domain.User;
import club.renxl.www.user.ssocenter.service.IUserLogin;
import club.renxl.www.user.ssocenter.service.IUserLoginInterceptor;

/**
 * <p>
 * 用户操作
 * </p>
 * 
 * @author renxl
 * @date 20180910
 * @version 1.0.0
 *https://www.cnblogs.com/ou-pc/p/7833172.html 手动实现tomcat
 */
@RestController
@RequestMapping("login")
public class UserLoginController {
	private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);
	@Autowired
	private IUserLogin iUserLogin;
	@Autowired
	private IUserLoginInterceptor iUserLoginInterceptor;
	
	/**
	 * 各子系统登录拦截器调用sso该在线验证接口
	 * 校验走redis,承载短时间并发校验
	 * 
	 * @param user 携带登录way信息，为app还是web;
	 * @param request 携带token等信息
	 * @return
	 */
	@RequestMapping("/online")
	public BaseResponse isOnline(@RequestBody User user) {
		// 从cookie中获取token;可能会存在跨域问题，进行一波测试
		String token =user.getToken();
		return iUserLoginInterceptor.isOnline(token, user.getLoginWay());
	}
	

	/**
	 * 登录，登录首先与redis进行比对;
	 * 
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loginin")
	public BaseResponse login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
		// 从cookie中获取token;可能会存在跨域问题，进行一波测试
		log.debug("login ==> " + user);
		return iUserLogin.login(user, request, response);
	}
	
	

	/**
	 * 登录，登录首先与redis进行比对;
	 * 
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loginout")
	public BaseResponse loginOut(@RequestBody User user,@RequestBody HttpServletRequest request, @RequestBody HttpServletResponse response) {
		// 从cookie中获取token;可能会存在跨域问题，进行一波测试
		// loginway+token
		return iUserLogin.loginOut(request, response, user);
	}
	
	

}
