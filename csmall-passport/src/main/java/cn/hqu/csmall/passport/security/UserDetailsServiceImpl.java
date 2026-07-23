package cn.hqu.csmall.passport.security;


import cn.hqu.csmall.passport.mapper.AdminMapper;
import cn.hqu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        AdminLoginInfoVO loginInfoVO = adminMapper.getLoginInfoByUsername(username);
        if (loginInfoVO == null) {
            log.debug("此用户名没有匹配用户数据");
            return null;
        }
        log.debug("此用户名有匹配用户数据，将返回UserDetails对象");
        //查询该用户的角色名列表
        List<String> roleNames = adminMapper.getRolesByUsername(username);
        log.debug("用户角色：{}", roleNames);
        String[] authorities = roleNames.toArray(new String[0]);
        UserDetails userDetails = User.builder()
                .username(loginInfoVO.getUsername())
                .password(loginInfoVO.getPassword())
                .disabled(loginInfoVO.getEnable() == 0)
                .accountLocked(false)
                .accountExpired(false)
                .authorities(authorities)
                .build();
        log.debug("根据用户名查询用户信息：{}", userDetails);
        return userDetails;
    }
}
