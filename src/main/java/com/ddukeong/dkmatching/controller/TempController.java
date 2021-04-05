package com.ddukeong.dkmatching.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class TempController {

    @RequestMapping("/")
    public String welcome(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            String userName = user.getUsername();
            String roles = user.getAuthorities().toString();
            model.addAttribute("userName", userName);
            model.addAttribute("role", roles);
            log.info("Current User : {}, roles : {}", userName, roles);
        }
        return "home.html";
    }

    @GetMapping("/anonymous")
    public String anonymous() {
        return "anonymous.html";
    }

    @PreAuthorize("@securityHandler.hasRole({'ROLE_ADMIN', 'ROLE_A'})")
    @GetMapping("/a")
    public String a() {
        return "user.html";
    }

    @PreAuthorize("@securityHandler.isAdmin()")
    @GetMapping("/admin")
    public String admin() {
        return "admin.html";
    }
}
