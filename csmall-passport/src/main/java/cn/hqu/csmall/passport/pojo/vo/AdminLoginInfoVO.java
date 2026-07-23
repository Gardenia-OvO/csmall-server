package cn.hqu.csmall.passport.pojo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLoginInfoVO implements Serializable {
    private String username;
    private String password;
    private Integer enable;
}
