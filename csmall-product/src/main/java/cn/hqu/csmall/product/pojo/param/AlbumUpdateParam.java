package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AlbumUpdateParam implements Serializable {
    @NotNull(message = "相册ID不能为空")
    @ApiModelProperty(value = "相册ID", required = true, example = "1")
    private Long id;
    @NotNull(message = "相册名不能为空")
    @ApiModelProperty(value = "相册名称",required = true,example = "相册1")
    private String name;
    @NotNull(message = "相册简介不能为空")
    @ApiModelProperty(value = "相册简介",required = true,example = "相册1的简介")
    private String description;
    @NotNull(message = "相册序号不能为空")
    @ApiModelProperty(value = "相册序号",required = true,example = "1")
    private Integer sort;
}
