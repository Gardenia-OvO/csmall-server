package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttributeUpdateParam implements Serializable {
    @ApiModelProperty(value = "属性名称", example = "颜色")
    private String name;
    @ApiModelProperty(value = "简介")
    private String description;
    @ApiModelProperty(value = "属性类型，1=销售属性，0=非销售属性")
    private Integer type;
    @ApiModelProperty(value = "输入类型")
    private Integer inputType;
    @ApiModelProperty(value = "备选值列表")
    private String valueList;
    @ApiModelProperty(value = "计量单位")
    private String unit;
    @ApiModelProperty(value = "排序序号")
    private Integer sort;
    @ApiModelProperty(value = "是否允许自定义")
    private Integer isAllowCustomize;
}
