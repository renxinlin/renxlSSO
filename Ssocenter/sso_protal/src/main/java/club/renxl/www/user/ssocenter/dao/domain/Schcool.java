package club.renxl.www.user.ssocenter.dao.domain;

import java.util.Date;

import lombok.Data;
@Data
public class Schcool {
    /**  */
    private Integer id;

    /** 机构唯一码 */
    private String code;

    /** 查询码 */
    private String seqCode;

    /** 名称 */
    private String name;

    /** 简称 */
    private String shortName;

    /** 传真 */
    private String fax;

    /** 具体地址 */
    private String address;

    /** 责任人 */
    private String resName;

    /** 责任人电话 */
    private String resPhone;

    /** 资格证书编号 */
    private String qualification;

    /** 描述 */
    private String description;

    /** 创建时间 */
    private Date createDate;

    /** 更新时间 */
    private Date updateDate;

    /** 类型：1,维保单位，2,物业单位(使用单位)，3,质检单位，4,救援单位，5,街道，6,社区，7厂商 ，8产权单位 9设计单位 10 制造单位 11电梯安装单位 */
    private Byte type;

    /** 机构状态 1使用中 0已禁用 */
    private String status;

    /** 机构状态 1已激活 0未激活 */
    private String activStatus;

 
}