package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BrandUpdateParam implements Serializable {
    @NotNull(message = "品牌ID不能为空")
    @ApiModelProperty(value = "品牌ID", required = true, example = "1")
    private Long id;
    @NotNull(message = "品牌名称不能为空")
    @ApiModelProperty(value = "品牌名称",required = true,example = "品牌1")
    private String name;
    @NotNull(message = "拼音不能为空")
    @ApiModelProperty(value = "拼音",required = true,example = "pinyin")
    private String pinyin;
    private String logo;
    private String description;
    private String keywords;
    @NotNull(message = "排序不能为空")
    @Range(min = 0, max = 99, message = "排序序号必须在0-99之间")
    private Integer sort;
    @NotNull(message = "启用状态不能为空")
    @Range(min = 0, max = 1, message = "启用状态必须在0-1之间")
    private Integer enable;
}
