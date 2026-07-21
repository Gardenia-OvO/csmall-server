package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AttributeTemplateUpdateParam implements Serializable {
    @NotNull(message = "属性模版ID不能为空")
    @ApiModelProperty(value = "属性模版ID", required = true, example = "1")
    private Long id;

    @NotNull(message = "属性模版名称不能为空")
    @ApiModelProperty(value = "属性模版名称", required = true, example = "模版1")
    private String name;

    @ApiModelProperty(value = "拼音", example = "moban1")
    private String pinyin;

    @ApiModelProperty(value = "关键词", example = "关键词1,关键词2")
    private String keywords;

    @ApiModelProperty(value = "排序序号", example = "1")
    private Integer sort;
}
