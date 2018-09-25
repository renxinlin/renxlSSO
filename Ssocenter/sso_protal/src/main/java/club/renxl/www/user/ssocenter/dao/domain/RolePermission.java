package club.renxl.www.user.ssocenter.dao.domain;

import lombok.Data;

@Data
public class RolePermission {
    /**  */
    private Integer id;

    /** 用户编码 */
    private Integer roleId;

    /** 权限id */
    private Integer permissionId;

 
}