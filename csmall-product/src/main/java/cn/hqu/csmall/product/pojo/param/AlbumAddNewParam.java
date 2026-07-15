package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AlbumAddNewParam implements Serializable {
    @ApiModelProperty(value = "相册名称",required = true,example = "相册1")
    private String name;
    @ApiModelProperty(value = "相册简介",required = true,example = "相册1的简介")
    private String description;
    @ApiModelProperty(value = "相册序号",required = true,example = "1")
    private Integer sort;
}
