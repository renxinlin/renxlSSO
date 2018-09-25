package club.renxl.www.user.ssocenter.dao.domain;

import java.util.Date;

import lombok.Data;



@Data
public class Permission {
    /**  */
    private Integer id;

    /** 名称 */
    private String name;

    /** 编码 */
    private String code;

    /** 类型0菜单1按钮 */
    private Byte type;

    /** 路径 */
    private String path;

    /** 页面路径 */
    private String page;

    /** 父级id,顶级为0 */
    private Integer pid;

    /** 状态 1启用 0禁用 */
    private String status;

    /** 顺序 */
    private Byte seq;

    /** 描述 */
    private String description;

    /** 创建时间 */
    private Date createTime;
 
}