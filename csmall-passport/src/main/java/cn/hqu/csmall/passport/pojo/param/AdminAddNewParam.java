package cn.hqu.csmall.passport.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增管理员入参类
 */
@Data
public class AdminAddNewParam implements Serializable {

    /**
     * 登录用户名
     */
    @NotNull(message = "添加管理员失败，管理员名字不能为空")
    @ApiModelProperty(value = "管理员用户名",required = true,example = "test01")
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "添加管理员失败，密码称不能为空")
    @ApiModelProperty(value = "密码",required = true,example = "123456")
    private String password;
    /**
     * 昵称
     */
    @NotNull(message = "添加管理员失败，昵称不能为空")
    @ApiModelProperty(value = "昵称",required = true,example = "test01")
    private String nickname;
    /**
     * 头像地址
     */
    @NotNull(message = "添加管理员失败，管理员头像不能为空")
    @ApiModelProperty(value = "管理员头像",required = true,example = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png")
    private String avatar;
    /**
     * 手机号
     */
    @NotNull(message = "添加管理员失败，手机号不能为空")
    @ApiModelProperty(value = "手机号",required = true,example = "17588888888")
    private String phone;
    /**
     * 邮箱
     */
    @NotNull(message = "添加管理员失败，邮箱不能为空")
    @ApiModelProperty(value = "邮箱",required = true,example = "test011@qq.com")
    private String email;
    /**
     * 个人描述
     */
    @NotNull(message = "添加管理员失败，简介称不能为空")
    @ApiModelProperty(value = "管理员简介",required = true,example = "可乐管理员")
    private String description;
    /**
     * 启用状态 0禁用 1启用
     */
    @NotNull(message = "添加管理员失败，启用不能为空")
    @ApiModelProperty(value = "是否启用",required = true,example = "0")
    private Integer enable;

}