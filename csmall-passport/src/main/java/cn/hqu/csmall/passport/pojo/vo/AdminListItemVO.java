package cn.hqu.csmall.passport.pojo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class AdminListItemVO implements Serializable {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private Integer enable;
    private String description;
}
