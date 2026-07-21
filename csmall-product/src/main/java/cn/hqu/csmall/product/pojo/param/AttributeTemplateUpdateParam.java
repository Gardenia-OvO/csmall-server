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
    @NotNull(message = "属性模版名不能为空")
    @ApiModelProperty(value = "属性模版名称",required = true,example = "测试模版001")
    private String name;
    @NotNull(message = "属性模版拼音不能为空")
    @ApiModelProperty(value = "属性模版拼音",required = true,example = "shuxingmobanpinyin")
    private String pinyin;
    @NotNull(message = "序号不能为空")
    @ApiModelProperty(value = "属性模版序号",required = true,example = "1")
    private Integer sort;
    @NotNull(message = "关键词不能为空")
    @ApiModelProperty(value = "属性模版关键词",required = true,example = "1")
    private String keywords;
}
