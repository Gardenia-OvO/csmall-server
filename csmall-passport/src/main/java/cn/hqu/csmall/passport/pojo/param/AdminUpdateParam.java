package cn.hqu.csmall.passport.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AdminUpdateParam implements Serializable {

    @NotNull(message = "修改管理员失败，ID不能为空")
    @ApiModelProperty(value = "管理员ID", required = true, example = "1")
    private Long id;

    @NotNull(message = "修改管理员失败，昵称不能为空")
    @ApiModelProperty(value = "管理员昵称", required = true, example = "张三")
    private String nickname;

    @NotNull(message = "修改管理员失败，头像不能为空")
    @ApiModelProperty(value = "管理员头像URL", required = true, example = "/avatar/default.png")
    private String avatar;

    @NotNull(message = "修改管理员失败，手机号不能为空")
    @ApiModelProperty(value = "管理员手机号", required = true, example = "13800138000")
    private String phone;

    @NotNull(message = "修改管理员失败，邮箱不能为空")
    @ApiModelProperty(value = "管理员邮箱", required = true, example = "admin@example.com")
    private String email;

    @NotNull(message = "修改管理员失败，描述不能为空")
    @ApiModelProperty(value = "管理员描述", required = true, example = "普通管理员")
    private String description;

    @NotNull(message = "修改管理员失败，启用状态不能为空")
    @ApiModelProperty(value = "是否启用，0=禁用，1=启用", required = true, example = "1")
    private Integer enable;
}
