package com.example.demo;

import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    private final Environment environment;

    public IndexController(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping("/")
    public String user(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            model.addAttribute("name", principal.getAttribute("name"));
        }

        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles != null && activeProfiles[0] != null) {
            model.addAttribute("activeProfile", activeProfiles[0]);
        }
        return "index.html";
    }

}
