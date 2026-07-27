package cn.hqu.csmall.merchant.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商家表实体类
 */
@Data
@TableName("mms_merchant")
public class Merchant implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商家名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 商家Logo
     */
    private String logo;

    /**
     * 商家简介
     */
    private String description;

    /**
     * 状态：0=待审核，1=已通过，2=已禁用
     */
    private Integer status;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;
}
