package club.renxl.www.user.ssocenter.httpapi;

import org.springframework.web.bind.annotation.RequestMapping;

import club.renxl.www.response.BaseResponse;

/**
 * <p>用户操作</p>
 * 
 * @author renxl
 * @date 20180910
 * @version 1.0.0
 *
 */
public class UserLoginController {
	
	/**
	  *  <p>登录；</p>
	  *  
	  *  此种类型并发不同于库存并发;
	  * 
	 * @return
	 */
	public BaseResponse login() {
	
		
		/*
		 *  动态切换redis
		 *  动态加载数据源
		 * if() {
			
		}*/
		// 校验表单
			// 失败返回
		//获取redis用户/失败获取db数据
		
			//失败返回
		// 登录信息存储，存储用户,权限(刷新过期时间为3天);/获取数据库信息存储;(双十一类似场景需要提前预热用户信息到redis集群，后切换redis连接到集群)
		//存储token-userid（手机15天,网页30分钟）
			// 异常返回
		
		// 
		
		
		
		// 登录次数限制，交给nginx limit模块
		// 
		
		return null;
		
	}
	
	/**
	 * 退出
	 * @return
	 */
	public BaseResponse loginOut() {
		return null;
		
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
	
	
	
	


}
