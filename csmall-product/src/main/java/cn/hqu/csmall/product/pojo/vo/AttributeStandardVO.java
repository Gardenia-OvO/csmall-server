package cn.hqu.csmall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttributeStandardVO implements Serializable {
    private Long id;
    private Long templateId;
    private String name;
    private String description;
    private Integer type;
    private Integer inputType;
    private String valueList;
    private String unit;
    private Integer sort;
    private Integer isAllowCustomize;
}
