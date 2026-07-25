package cn.hqu.csmall.passport.security;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginPrincipal {
    private Long id;
    private String username;
}
