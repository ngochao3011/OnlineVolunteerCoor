/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.TaiKhoan;
import com.uef.model.Volunteer;
import com.uef.service.TaiKhoanService;
import com.uef.service.VolunteerService;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author ADMIN
 */
@Controller
public class AccountController {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @GetMapping("/account")
    public String accountPage(HttpSession session, Model model) {
        if (session != null && session.getAttribute("user") != null) {
            TaiKhoan user = (TaiKhoan) session.getAttribute("user");

            Volunteer thanhVien = volunteerService.getVolunteerById(user.getMaTaiKhoan());
            model.addAttribute("thanhVien", thanhVien);
            model.addAttribute("pageTitle", "Thông tin tài khoản");
            model.addAttribute("customCss", "/src/css/template-custom.css");
            model.addAttribute("pageContent", "/WEB-INF/views/account.jsp");
            return "layout/layoutmaster";
        } else {
            model.addAttribute("pageTitle", "Đăng nhập");
            model.addAttribute("customCss", "/src/css/template-signin-signup.css");
            model.addAttribute("pageContent", "/WEB-INF/views/signin-signup.jsp");
            return "layout/layoutmaster";
        }

    }

    @PostMapping("/updateAccount")
    public String updateAccount(
            @RequestParam("email") String email,
            @RequestParam("avatarFile") MultipartFile avatar,
            @RequestParam("hoTen") String hoTen,
            @RequestParam("sdt") String sdt,
            @RequestParam("diaChi") String diaChi,
            HttpSession session) throws IOException {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        // Cập nhật thông tin cơ bản
        Volunteer volunteer = new Volunteer();
        volunteer.setMaThanhVien(user.getMaTaiKhoan());
        volunteer.setHoTen(hoTen);
        volunteer.setSdt(sdt);
        volunteer.setDiaChi(diaChi);

        if (!avatar.isEmpty()) {

            String fileName = UUID.randomUUID() + "_" + avatar.getOriginalFilename();
            String uploadDir = "D:/uploads/";
            File qrDir = new File(uploadDir);
            if (!qrDir.exists()) {
                qrDir.mkdirs(); // tạo cả chuỗi thư mục nếu cần
            }
            File dest = new File(uploadDir + fileName);
            avatar.transferTo(dest);
            volunteer.setUrlAvatar("/images/uploads/" + fileName);
            session.setAttribute("urlAvatar", "/images/uploads/" + fileName);

        }
        volunteerService.updateAccount(volunteer);

        if (!email.equals(user.getEmail())) {
            taiKhoanService.updateEmail(user.getMaTaiKhoan(), email);
        }
        user.setEmail(email);
        session.setAttribute("user", user);

        return "redirect:/account";
    }

    @PostMapping("/changePassword")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        // Kiểm tra mật khẩu hiện tại
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user != null && !encoder.matches(currentPassword, user.getMatKhau())) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu hiện tại không đúng!");
            return "redirect:/account";
        }

        // Kiểm tra mật khẩu mới
        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới không khớp!");
            return "redirect:/account";
        }

        // Cập nhật mật khẩu mới
        String encodedNew = encoder.encode(newPassword);
        taiKhoanService.updatePassword(user.getMaTaiKhoan(), encodedNew);
        user.setMatKhau(encodedNew);
        session.setAttribute("user", user);
        redirectAttributes.addFlashAttribute("success", "Đổi mật khẩu thành công!");

        return "redirect:/account";
    }
}
