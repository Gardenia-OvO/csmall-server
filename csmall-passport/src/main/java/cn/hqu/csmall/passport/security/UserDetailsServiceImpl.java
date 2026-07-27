package cn.hqu.csmall.passport.security;


import cn.hqu.csmall.passport.mapper.AdminMapper;
import cn.hqu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Spring Security框架自动调用UserDetailsServiceImpl.loadUserByUsername");
        //根据用户名查询用户数据
        AdminLoginInfoVO logInfo = adminMapper.getLoginInfoByUsername(username);
        log.debug("根据用户名{}查询用户数据，结果为{}",username,logInfo);
        if(logInfo==null){
            log.debug("此用户名没有匹配用户数据，将返回null");
            return null;
        }
        log.debug("此用户名有匹配用户数据，将返回UserDetails对象");
     /*   UserDetails userDetails = User.builder()
                                      .username(logInfo.getUsername())
                                      .password(logInfo.getPassword())
                                      .disabled(logInfo.getEnable()==0)//账户是否禁用
                                       .accountLocked(false)//账户是否锁定
                                       .accountExpired(false)//账户是否过期
                                       .credentialsExpired(false)//密码是否过期
                                       .authorities("这是一个临时使用的山寨权限")
                                       .build();*/
        //封装权限信息
        List<String> permissions = logInfo.getPermissions();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
       for (String permission : permissions) {
           SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
           authorities.add(authority);
       }

        // root用户拥有最高权限，追加所有模块的权限
        if ("root".equals(username)) {
           String[] allPermissions = {
               // AMS - 管理员与权限管理
               "/ams/admin/read", "/ams/admin/add-new", "/ams/admin/update", "/ams/admin/delete",
               // PMS - 商品类别管理
               "/pms/category/read", "/pms/category/add-new", "/pms/category/update", "/pms/category/delete",
               // PMS - 品牌管理
               "/pms/brand/read", "/pms/brand/add-new", "/pms/brand/update", "/pms/brand/delete",
               // PMS - 属性模板管理
               "/pms/attribute-template/read", "/pms/attribute-template/add-new", "/pms/attribute-template/update", "/pms/attribute-template/delete",
               // PMS - 相册管理
               "/pms/album/read", "/pms/album/add-new", "/pms/album/update", "/pms/album/delete",
               // MMS - 商家管理
               "/mms/merchant/read", "/mms/merchant/add-new", "/mms/merchant/update", "/mms/merchant/delete",
               // OMS - 订单管理
               "/oms/order/read", "/oms/order/add-new", "/oms/order/update", "/oms/order/delete"
           };
           for (String perm : allPermissions) {
               authorities.add(new SimpleGrantedAuthority(perm));
           }
        }
        //将查询到的用户数据封装到AdminDetail对象中
        AdminDetail adminDetail = new AdminDetail(logInfo.getId(),
                logInfo.getUsername(),logInfo.getPassword(),
                logInfo.getEnable()==1,authorities);
        log.debug("即将向Spring Security返回UserDetails对象：{}",adminDetail);
        return adminDetail;
    }
}
