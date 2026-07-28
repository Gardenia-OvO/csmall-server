package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PictureAddNewParam implements Serializable {
    @NotNull @ApiModelProperty(value = "相册ID", required = true, example = "1")
    private Long albumId;
    @NotNull @ApiModelProperty(value = "图片URL", required = true)
    private String url;
    @ApiModelProperty(value = "图片描述", example = "商品主图")
    private String description;
    @ApiModelProperty(value = "排序号", example = "1")
    private Integer sort;
}
