package club.renxl.www.user.ssocenter.dao.domain;

import java.util.Date;
import java.util.List;

import club.renxl.www.login.util.Permission;
import lombok.Data;
@Data
public class User {

	/**
	 * 登录类型，见常量LoginType qq,微博，用户名，手机等
	 */
	private String loginType;
	/**
	 * 登录方式:1web;2app
	 */
	private String loginWay;
	
	/**
	 * 登录时保存的token 
	 */
	private String token;
	/**
	 * 权限集合
	 */
	private List<Permission> permissions;
	
	
	
	
	
	
	  /** 编号 */
    private Long id;

    /** 登录名 */
    private String username;

    /** 密码 */
    private String password;

    /** 状态 1可用 0禁用 */
    private Boolean status;

    /** 最后登录时间 */
    private Date lastLoginTime;

    /** 登录IP */
    private String lastLoginIp;

    /** 姓名 */
    private String realName;

    /** 身份证号 */
    private String idCard;

    /** 邮箱 */
    private String email;

    /** 手机号码 */
    private String userPhone;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 区县 */
    private String area;

    /** 详细地址 */
    private String address;

    /** 头像 */
    private String nickImg;

    /** 昵称 */
    private String nickName;

    /** 角色集合，逗号分隔；所有用户只有角色概念；部门具有角色但本身不是角色 */
    private String role;

    /** 支付宝 */
    private String alipay;

    /**  */
    private String qq;

    /**  */
    private String wx;

    /**  */
    private String others;

    /**  */
    private String alipayJsonData;

    /**  */
    private String wxJsonData;

    /**  */
    private String appType;

    /**  */
    private String appUser;

    /** 第三方凭证 */
    private String appToken;

    /** 1老师2学生3学校管理人员4其他 */
    private String schoolUserType;

    /** 学生一卡通账号，老师，老师一卡通账号或编号等等 */
    private Integer schoolCardId;

    /** 年级1，2，3，4，（研究生）5，6，7（博士）a,b,c */
    private String schoolClass;

    /** 当前主专业 */
    private String schoolSubject;

    /** 双修专业，或历史专业，换专业等等的学生,逗号分隔 */
    private String schoolOtherSubject;

    /** 是否已经毕业1大学中，2留级生，3毕业 */
    private String schoolGraduate;

    /** 大学待几年，默认4年 */
    private Integer schoolYear;

    /** 学校名称;后期需要添加code */
    private String schoolName;

    /** 学院名称,后期需要添加code */
    private String schoolAcademy;

    /** 省份代码 */
    private String provinceCode;

    /** 区县代码 */
    private String areaCode;

    /** 城市代码 */
    private String cityCode;

    /** 注册时间 */
    private Date registerTime;

    /** 修改时间 */
    private Date modifyTime;

    /** 删除标记:0已删除 1未删除 */
    private String isDeleted;

    /** 身份证正面 */
    private String idCardImgFace;

    /** 身份证反面 */
    private String idCardImgInfo;

    /** 学校id */
    private Integer schoolId;

    /** 学院id */
    private Integer schoolAcademyId;

    /** 主专业id */
    private Integer schoolSubjectId;

    /** 行政区域code */
    private String provincesId;
}