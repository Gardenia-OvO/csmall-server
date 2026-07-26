package cn.hqu.csmall.passport.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增角色入参类
 */
@Data
public class RoleAddNewParam implements Serializable {

    /**
     * 角色名称
     */
    @NotNull(message = "添加角色失败，角色名称不能为空")
    @ApiModelProperty(value = "角色名称", required = true, example = "超级管理员")
    private String name;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述", example = "拥有所有权限的管理员")
    private String description;

    /**
     * 排序
     */
    @NotNull(message = "添加角色失败，排序不能为空")
    @Range(min = 0, max = 99, message = "排序序号必须在0-99之间")
    @ApiModelProperty(value = "排序序号", required = true, example = "1")
    private Integer sort;

    /**
     * 启用状态 0禁用 1启用
     */
    @NotNull(message = "添加角色失败，启用状态不能为空")
    @Range(min = 0, max = 1, message = "启用状态必须在0-1之间")
    @ApiModelProperty(value = "是否启用", required = true, example = "1")
    private Integer enable;}
