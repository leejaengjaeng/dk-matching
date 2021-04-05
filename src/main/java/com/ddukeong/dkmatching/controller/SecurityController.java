package com.ddukeong.dkmatching.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
    @RequestMapping("/auth/login")
    public String login(Model model) {
        return "auth/login.html";
    }

    @RequestMapping(path = {"/auth/login/success", "/auth/logout/success"})
    public String afterSuccess() {
        return "redirect:/";
    }

    @RequestMapping("/auth/denied")
    public String accessDenied(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("userName", user == null ? "NULL" : user.getUsername());
        model.addAttribute("role", user == null ? "NULL" : user.getAuthorities().toString());
        return "auth/accessDeny.html";
    }
}
