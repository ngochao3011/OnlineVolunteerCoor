
package com.uef.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "homepage";
    }
    @GetMapping("/signin")
    public String showSigninPage() {
        return "signin-signup";
    }
    @GetMapping("/activity")
    public String showActivityPage() {
        return "activitylist";
    }
}
