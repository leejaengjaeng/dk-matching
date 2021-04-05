package com.ddukeong.dkmatching.config.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class DkUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            return new User(username, Strings.EMPTY, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else {
            return new User(username, Strings.EMPTY, Collections.singletonList(new SimpleGrantedAuthority("ROLE_A")));
        }
    }
}