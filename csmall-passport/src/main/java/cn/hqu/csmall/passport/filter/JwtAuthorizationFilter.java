package cn.hqu.csmall.passport.filter;


import cn.hqu.csmall.passport.security.LoginPrincipal;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${csmall.jwt.secret-key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.debug("JwtAuthorizationFilter过滤器开始执行");
        String jwt = request.getHeader("Authorization");
        log.debug("获取到的JWT是：{}",jwt);
        if(!StringUtils.hasText(jwt)){
            chain.doFilter(request,response);
            return;
        }

        //解析JWT
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            log.warn("解析JWT失败，JWT：{}，异常：{}", jwt, e.getMessage());
            chain.doFilter(request, response);
            return;
        }

        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        log.debug("解析JWT得到id是：{}，username是：{}",id,username);

        //封装当事人
        Object principals = new LoginPrincipal().setId(id).setUsername(username);
        Object credentials = null;

        //从JWT中解析真实权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        String permissionsJson = claims.get("permissions", String.class);
        if (StringUtils.hasText(permissionsJson)) {
            List<String> permissions = JSON.parseArray(permissionsJson, String.class);
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
            log.debug("从JWT中解析到权限：{}", permissions);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(principals,credentials,authorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        chain.doFilter(request,response);
    }
}
