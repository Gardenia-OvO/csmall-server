package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CategoryAddNewParam implements Serializable {
    @NotNull(message = "类别名称不能为空")
    @ApiModelProperty(value = "类别名称", required = true, example = "类别1")
    private String name;

    @NotNull(message = "父级ID不能为空")
    @ApiModelProperty(value = "父级ID", required = true, example = "0")
    private Long parentId;

    @ApiModelProperty(value = "关键词", example = "关键词1,关键词2")
    private String keywords;

    @NotNull(message = "排序不能为空")
    @Range(min = 0, max = 99, message = "排序序号必须在0-99之间")
    @ApiModelProperty(value = "排序序号", required = true, example = "1")
    private Integer sort;

    @ApiModelProperty(value = "图标", example = "http://example.com/icon.png")
    private String icon;

    @NotNull(message = "启用状态不能为空")
    @Range(min = 0, max = 1, message = "启用状态必须在0-1之间")
    @ApiModelProperty(value = "启用状态", required = true, example = "1")
    private Integer enable;

    @ApiModelProperty(value = "是否显示", example = "1")
    private Integer isDisplay;
}
