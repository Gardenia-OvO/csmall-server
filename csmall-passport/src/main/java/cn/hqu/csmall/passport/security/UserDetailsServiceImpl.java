package cn.hqu.csmall.passport.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("执行了loadUserByUsername方法，参数是：{}", username);
        if(!"root".equals(username)){
            log.debug("此用户名没有匹配用户数据");
            return  null;
        }
        log.debug("此用户名有匹配用户数据，将返回UserDatils对象");
        UserDetails userDetails = User.builder()
                .username("root")
                .password("{noop}123456")
                .disabled(false)
                .accountLocked(false)
                .accountExpired(false)
                .authorities("这是一个临时使用的山寨权限")
                .build();
        log.debug("根据用户名查询用户信息：{}", userDetails);
        return userDetails;
    }
}
