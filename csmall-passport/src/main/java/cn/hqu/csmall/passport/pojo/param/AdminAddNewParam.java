package cn.hqu.csmall.passport.pojo.param;

import java.time.LocalDateTime;

public class AdminAddNewParam {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 描述
     */
    private String description;
    /**
     * 是否启用 0禁用 1启用
     */
    private Integer enable;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 最后登录时间
     */
    private LocalDateTime gmtLastLogin;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    public Object getUsername() {
        return null;
    }

    public void setUsername(String test04) {
    }

    public void setPassword(String number) {
    }

    public void setAvatar(String image) {
    }

    public void setDescription(String test04的描述) {
    }
}