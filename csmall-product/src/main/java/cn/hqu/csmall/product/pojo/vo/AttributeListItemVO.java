package cn.hqu.csmall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttributeListItemVO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Integer type;
    private Integer inputType;
    private String valueList;
    private String unit;
    private Integer sort;
    private Integer isAllowCustomize;
}
