package com.ddukeong.dkmatching.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(getAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        return new DkAuthenticationProvider(getUserDetailsService());
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new DkUserDetailService();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
                .antMatchers(
                        "/monitor/*",
                        "/**/*.jpg",
                        "/**/*.jpeg",
                        "/**/*.png",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.gif",
                        "/**/*.ttf",
                        "/**/*.wof",
                        "/**/*.woff",
                        "/**/*.woff2");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf().disable()
            .headers().disable();
        http.authorizeRequests()
                .antMatchers("/", "/jpa/**/*")
                .permitAll()
                .anyRequest()
                .fullyAuthenticated()
            .and()
            .formLogin()
                .loginPage("/auth/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/internal/auth/login")
                .defaultSuccessUrl("/auth/login/success")
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/logout/success")
                .permitAll()
            .and()
                .exceptionHandling()
                .accessDeniedPage("/auth/denied");
        // @formatter:on
    }
}