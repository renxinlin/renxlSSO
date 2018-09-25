package club.renxl.www.user.ssocenter.constants;

/**
 * 登录类别
 * @author renxl
 * @date 20180911
 * @version 1.0.0
 *
 */
public class LoginConstant {
	/**
	 * 微博登录
	 */
	public static final String WEIBO="1";
	/**
	 * qq登录
	 */
	public static final String QQ="2";
	/**
	 * 微信登录
	 */
	public static final String WEIXIN="3";
	
	/**
	 * 手机登录
	 */
	public static final String PHONE="4";
	/**
	 * 用户名登录
	 */
	public static final String USERNAME="5";
	
	
	
	/**
	 * 手机登录
	 */
	public static final String APP="2";
	/**
	 * 网页登录
	 */
	public static final String WEB="1";
	/**
	 * APP登录有效时间,十五天
	 */
	public static final long APP_TIME = 15*24*60*60;
	/**
	 * WEB登录有效时间,30分钟
	 */
	public static final long WEB_TIME = 30*60;
	/**
	 * 登录token
	 */
	public static final String TOKEN = "token";

}
