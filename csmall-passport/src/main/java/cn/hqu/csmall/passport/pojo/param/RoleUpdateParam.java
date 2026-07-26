package cn.hqu.csmall.passport.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RoleUpdateParam implements Serializable {
    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(value = "角色ID", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "角色名称", example = "超级管理员")
    private String name;

    @ApiModelProperty(value = "角色描述", example = "拥有所有权限的管理员")
    private String description;

    @Range(min = 0, max = 99, message = "排序序号必须在0-99之间")
    @ApiModelProperty(value = "排序序号", example = "1")
    private Integer sort;

    @Range(min = 0, max = 1, message = "启用状态必须在0-1之间")
    @ApiModelProperty(value = "是否启用", example = "1")
    private Integer enable;
}
