package cn.hqu.csmall.passport.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AdminDetail extends User {
    @Getter
    private Long id;

    public AdminDetail(Long id, String username, String password,
                       boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true,
                true, authorities);
        this.id = id;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }
}
