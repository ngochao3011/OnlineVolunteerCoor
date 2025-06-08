/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.TaiKhoan;
import com.uef.model.TaiKhoanDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author ADMIN
 */
@Controller
public class SigninSignupController {
    @Autowired
    private TaiKhoanDAO taiKhoanDAO;
        
    @GetMapping("/sign-up")
    public String showRegisterPage(Model model) {
        model.addAttribute("taiKhoan", new TaiKhoan());
        return "signin-signup";
    }
    
    @PostMapping("/sign-up")
    public String processRegister(@ModelAttribute TaiKhoan taiKhoan, RedirectAttributes redirect) {
        if (taiKhoanDAO.findByEmail(taiKhoan.getEmail()) != null) {
            redirect.addFlashAttribute("error", "Email đã tồn tại.");
            return "redirect:/sign-up";
        }

        taiKhoan.setMatKhau(new BCryptPasswordEncoder().encode(taiKhoan.getMatKhau()));
        taiKhoan.setQuyenHan("Tình nguyện viên");
        taiKhoanDAO.save(taiKhoan);
        redirect.addFlashAttribute("success", "Đăng ký thành công. Mời đăng nhập.");
        return "redirect:/sign-in";
    }
    
    @GetMapping("/sign-in")
    public String showLoginForm() {
        return "signin-signup";
    }
    
    @PostMapping("/sign-in")
    public String processLogin(@RequestParam String email,
                               @RequestParam String matKhau,
                               HttpSession session,
                               RedirectAttributes redirect) {
        TaiKhoan taiKhoan = taiKhoanDAO.findByEmail(email);
        if (taiKhoan != null && new BCryptPasswordEncoder().matches(matKhau, taiKhoan.getMatKhau())) {
            session.setAttribute("user", taiKhoan);
            return "redirect:/";
        }
        redirect.addFlashAttribute("error", "Sai email hoặc mật khẩu.");
        return "redirect:/sign-in";
    }
}
