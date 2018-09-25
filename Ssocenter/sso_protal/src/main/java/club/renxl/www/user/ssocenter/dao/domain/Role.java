package club.renxl.www.user.ssocenter.dao.domain;

import java.util.Date;

import lombok.Data;
@Data
public class Role {
    /**  */
    private Integer id;

    /**  */
    private String roleName;

    /**  */
    private Long createUserId;

    /**  */
    private String createUserName;

    /**  */
    private Integer modifyUserId;

    /**  */
    private String modifyUserName;

    /**  */
    private Date createDate;

    /**  */
    private Date modifyDate;

    /** 0����1���� */
    private String status;

    /**  */
    private String isDeleted;

     
}