package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AttributeAddNewParam implements Serializable {
    @NotNull(message = "属性模板ID不能为空")
    @ApiModelProperty(value = "所属属性模板ID", required = true, example = "1")
    private Long templateId;
    @NotNull(message = "属性名称不能为空")
    @ApiModelProperty(value = "属性名称", required = true, example = "颜色")
    private String name;
    @ApiModelProperty(value = "简介", example = "手机机身颜色")
    private String description;
    @ApiModelProperty(value = "属性类型，1=销售属性，0=非销售属性", example = "1")
    private Integer type;
    @ApiModelProperty(value = "输入类型，0=手动录入，1=单选，2=多选，3=单选下拉，4=多选下拉", example = "1")
    private Integer inputType;
    @ApiModelProperty(value = "备选值列表", example = "黑色,白色,金色")
    private String valueList;
    @ApiModelProperty(value = "计量单位")
    private String unit;
    @ApiModelProperty(value = "排序序号", example = "99")
    private Integer sort;
    @ApiModelProperty(value = "是否允许自定义，1=允许，0=禁止", example = "0")
    private Integer isAllowCustomize;
}
