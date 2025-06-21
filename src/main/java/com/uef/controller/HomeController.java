package com.uef.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Online Volunteer Coor");
        model.addAttribute("pageContent", "/WEB-INF/views/homepage.jsp");
        return "layout/layoutmaster";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/403")
    public String errorPage(HttpSession session, Model model) {
        model.addAttribute("pageTitle", "Error");
        model.addAttribute("pageContent", "/WEB-INF/views/403.jsp");
        return "layout/layoutmaster";
    }
}
