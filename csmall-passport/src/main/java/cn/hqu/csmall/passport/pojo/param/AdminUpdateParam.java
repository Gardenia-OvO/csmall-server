package cn.hqu.csmall.passport.pojo.param;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AdminUpdateParam implements Serializable {

    @NotNull(message = "修改管理员失败，ID不能为空")
    @ApiModelProperty(value = "管理员ID", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "管理员昵称", example = "张三")
    private String nickname;

    @ApiModelProperty(value = "管理员头像URL", example = "/avatar/default.png")
    private String avatar;

    @ApiModelProperty(value = "管理员手机号", example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "管理员邮箱", example = "admin@example.com")
    private String email;

    @ApiModelProperty(value = "管理员描述", example = "普通管理员")
    private String description;

    @ApiModelProperty(value = "是否启用，0=禁用，1=启用", example = "1")
    private Integer enable;

    @ApiModelProperty(value = "角色ID数组", example = "[1,2]")
    private List<Long> roleIds;

    /**
     * 兼容前端发字符串格式 "1,2,3" 或数组格式 [1,2,3]
     */
    @JsonSetter
    public void setRoleIds(Object value) {
        if (value == null) {
            this.roleIds = null;
        } else if (value instanceof List) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) value;
            this.roleIds = list.stream()
                    .map(v -> v instanceof Number ? ((Number) v).longValue() : Long.valueOf(v.toString()))
                    .collect(Collectors.toList());
        } else if (value instanceof Object[]) {
            this.roleIds = Arrays.stream((Object[]) value)
                    .map(v -> v instanceof Number ? ((Number) v).longValue() : Long.valueOf(v.toString()))
                    .collect(Collectors.toList());
        } else {
            // 字符串格式："1,2,3" 或 "[1,2,3]"
            String str = value.toString().trim();
            if (str.startsWith("[")) {
                str = str.substring(1, str.length() - 1);
            }
            if (str.isEmpty()) {
                this.roleIds = null;
                return;
            }
            this.roleIds = Arrays.stream(str.split(","))
                    .map(String::trim)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        }
    }
}
