package com.uef.controller;

import com.uef.model.TaiKhoan;
import com.uef.model.Volunteer;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private void addLoginInfoToModel(Model model, HttpSession session) {
        TaiKhoan loggedInAccount = (TaiKhoan) session.getAttribute("loggedInAccount");
        Volunteer loggedInProfile = (Volunteer) session.getAttribute("loggedInProfile");

        model.addAttribute("loggedInAccount", loggedInAccount);
        model.addAttribute("loggedInProfile", loggedInProfile);
        model.addAttribute("isLoggedIn", loggedInAccount != null && loggedInProfile != null);
    }

    @RequestMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("pageTitle", "Online Volunteer Coor");
        model.addAttribute("pageContent", "/WEB-INF/views/homepage.jsp");

        addLoginInfoToModel(model, session); 

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

        addLoginInfoToModel(model, session); 

        return "layout/layoutmaster";
    }
}
