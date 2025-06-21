/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.TaiKhoan;
import com.uef.model.Volunteer;
import com.uef.service.VolunteerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@Controller
public class AccountController {

    @Autowired
    private VolunteerService volunteerService;

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
    public String updateAccount(@ModelAttribute Volunteer thanhVien,
            @RequestParam("avatarFile") MultipartFile avatarFile,
            HttpServletRequest request) {

        if (!avatarFile.isEmpty()) {
            String uploadDir = request.getServletContext().getRealPath("/uploads/");
            String fileName = avatarFile.getOriginalFilename();
            File file = new File(uploadDir + fileName);
            try {
                avatarFile.transferTo(file);
                thanhVien.setUrlAvatar("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //thanhVienService.update(thanhVien); // cập nhật thông tin
        return "redirect:/account";
    }
}
