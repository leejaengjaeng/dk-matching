package com.ddukeong.dkmatching.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class SecurityHandler {

    private final UserContext userContext;

    private static final GrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");

    public SecurityHandler(UserContext userContext) {
        this.userContext = userContext;
    }

    public boolean isAdmin() {
        return userContext.getCurrentUser().getAuthorities().stream().anyMatch(el -> ROLE_ADMIN.getAuthority().equals(el.getAuthority()));
    }

    public boolean hasRole(String[] roles) {
        return userContext.getCurrentUser().getAuthorities().stream().anyMatch(element -> Arrays.asList(roles).contains(element.getAuthority()));
    }
}
