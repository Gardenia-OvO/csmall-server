package cn.hqu.csmall.commons.pojo.po;


import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLoginInfoPO implements Serializable {
    //管理员id
    private Long id;

    //管理员的启用状态
    private Integer enable;

    //管理员登录的IP地址
    private String remoteAddr;

    //管理员登录的浏览器版本
    private String userAgent;

    //管理员的权限列表的JSON字符串
    private String authorityListJsonString;

}
