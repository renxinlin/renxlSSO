package club.renxl.www.user.ssocenter.dao.domain;

import lombok.Data;

@Data
public class UserRole {
    /**  */
    private Integer id;

    /**  */
    private Long userId;

    /**  */
    private Integer roleId;

    /**  */
    private Long createUserId;

    /**  */
    private String createUserName;

    /**  */
    private String modifyUserId;

    /**  */
    private String modifyUserName;
 
}