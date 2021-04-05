package com.ddukeong.dkmatching.config.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserContext {
    /**
     * Get current user object from context.
     *
     * @return current user;
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new AuthenticationCredentialsNotFoundException("No authentication");
        }
        Object obj = auth.getPrincipal();
        if ("anonymousUser".equals(obj)) {
            return null;
        }
        if (!(obj instanceof User)) {
            throw new AuthenticationCredentialsNotFoundException("Invalid authentication with " + obj);
        }
        return (User) obj;
    }
}