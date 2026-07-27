package cn.hqu.csmall.passport.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改管理员密码入参类
 */
@Data
public class AdminPasswordUpdateParam implements Serializable {

    /**
     * 管理员ID
     */
    @NotNull(message = "修改管理员密码失败，管理员ID不能为空")
    @Range(min = 1, message = "修改管理员密码失败，请提供合法的管理员ID")
    @ApiModelProperty(value = "管理员ID", required = true, example = "1")
    private Long id;

    /**
     * 新密码
     */
    @NotNull(message = "修改管理员密码失败，新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true, example = "newPassword123")
    private String password;

}
