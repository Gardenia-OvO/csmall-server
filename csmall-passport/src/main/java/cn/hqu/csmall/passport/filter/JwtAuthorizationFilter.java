package cn.hqu.csmall.passport.filter;


import cn.hqu.csmall.passport.security.LoginPrincipal;
import org.springframework.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.debug("JwtAuthorizationFilter过滤器开始执行");
        String jwt = request.getHeader("Authorization");
        log.debug("获取到的JWT是：{}",jwt);
        if(!StringUtils.hasText(jwt)){
            //放行(请求头中没有头部）
            chain.doFilter(request,response);
            return;
        }
        //ToDO 当前类型和AdminServiceImpl中都声明了相同变量SecretKey，这是不合理的
        //ToDo 解析JWT的过程中可能出现异常需要处理

        //解析JWT
        String secretKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OTUyNywidXNlcm5hbWUiOiJ6aGFuZ3NhbiJ9.bCQuAAQo0GoVyxHiLcg3tCk2UYl1l0_DtBM-4GX4300";
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();

        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        log.debug("解析JWT得到id是：{}，username是：{}",id,username);

        //根据JWT解析结果，封装认证对象
        //Spring Security框架不介意当事人是什么类型，包含什么数据由程序员自己决定
        //程序员根据controller需要的数据来封装一个类型
        Object principals = new LoginPrincipal().setId(id).setUsername(username);
        Object credentials = null;
        //ToDO 这里的权限应该是真权限，暂时用假权限代替
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("山寨权限");
        authorities.add(authority);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principals,credentials,authorities);
        //将解析的结果封装为认证信息后，放在SpringSecurity的上下文中
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        //放行
        chain.doFilter(request,response);

    }
}