/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.TaiKhoan;
import com.uef.model.Volunteer;
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
import com.uef.service.TaiKhoanService;
import com.uef.service.VolunteerService;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
@Controller
public class SigninSignupController {
    @Autowired
    private TaiKhoanService taiKhoanService;
    
    @Autowired
    private VolunteerService volunteerService;
        
    @GetMapping("/sign-up")
    public String showRegisterPage(Model model) {
        model.addAttribute("taiKhoan", new TaiKhoan());
        return "signin-signup";
    }
    
    @PostMapping("/sign-up")
    public String processRegister(@ModelAttribute TaiKhoan taiKhoan,
                                @RequestParam String hoTen,
                                @RequestParam String sdt,
                                @RequestParam String xacNhanMatKhau, 
                                RedirectAttributes redirect) {
        if (taiKhoanService.getEmail(taiKhoan.getEmail()) != null) {
            redirect.addFlashAttribute("error", "Email đã tồn tại.");
            return "redirect:/sign-up";
        }
        
        if (!taiKhoan.getMatKhau().equals(xacNhanMatKhau)){
            redirect.addFlashAttribute("error", "Mật khẩu không khớp. Vui lòng nhập lại!");
            return "redirect:/sign-up";
        }

        taiKhoan.setMatKhau(new BCryptPasswordEncoder().encode(taiKhoan.getMatKhau()));
        taiKhoan.setQuyenHan("Tình nguyện viên");
        if (!taiKhoanService.dangKyTaiKhoan(taiKhoan)){
            redirect.addFlashAttribute("error", "Có lỗi khi đăng ký tài khoản!");
            return "redirect:/sign-up";
        }
        
        Integer id = taiKhoanService.getID(taiKhoan.getEmail());
        if (id == null) {
            redirect.addFlashAttribute("error", "Không tìm thấy ID tài khoản sau khi đăng ký!");
            return "redirect:/sign-up";
        }
        Volunteer volunteer = new Volunteer();
        volunteer.setMaTNV(id);
        volunteer.setHoTen(hoTen);
        volunteer.setSdtDienThoai(sdt);
        volunteer.setDiaChi("");
        volunteer.setTrangThai("Đã đăng ký");
        // Lấy ngày hiện tại của hệ thống
        Date now = new Date();
        volunteer.setNgayDangKy(now);
        if (!volunteerService.addVolunteer(volunteer)){
            redirect.addFlashAttribute("error", "Có lỗi khi đăng ký thông tin cá nhân!");        
            return "redirect:/sign-up";
        }
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
        TaiKhoan taiKhoan = taiKhoanService.getEmail(email);
        if (taiKhoan != null && new BCryptPasswordEncoder().matches(matKhau, taiKhoan.getMatKhau())) {
            session.setAttribute("user", taiKhoan);
            return "redirect:/";
        }
        redirect.addFlashAttribute("error", "Sai email hoặc mật khẩu.");
        return "redirect:/sign-in";
    }
}
