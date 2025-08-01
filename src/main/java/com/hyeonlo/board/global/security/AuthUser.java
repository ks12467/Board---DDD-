package com.hyeonlo.board.global.security;

import com.hyeonlo.board.domain.user.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {

    private final Long userId;
    private final String loginId;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUser(Long userId, String loginId, UserRole userRole) {
        this.userId = userId;
        this.loginId = loginId;
        this.authorities = List.of(
                new SimpleGrantedAuthority(userRole.name())
        );
    }
}
