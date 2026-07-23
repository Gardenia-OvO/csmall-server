package cn.hqu.csmall.passport.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AdminLoginInfoParam implements Serializable {

    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "管理员用户名",required = true,example = "test001")
    private String username;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "管理员密码",required = true,example = "123456")
    private String password;
}
