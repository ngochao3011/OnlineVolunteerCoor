package com.uef.controller;

import com.uef.model.TaiKhoan;
import com.uef.model.Volunteer;
import com.uef.service.TaiKhoanService;
import com.uef.service.VolunteerService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;

@Controller
public class SigninSignupController {

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("/sign-up")
    public String showRegisterPage(Model model) {
        model.addAttribute("taiKhoan", new TaiKhoan());
        model.addAttribute("pageTitle", "Đăng ký");
        model.addAttribute("customCss", "/src/css/template-signin-signup.css");
        model.addAttribute("pageContent", "/WEB-INF/views/signin-signup.jsp");
        return "layout/layoutmaster";
    }

    @PostMapping("/sign-up")
    public String processRegister(@ModelAttribute TaiKhoan taiKhoan,
            @RequestParam String hoTen,
            @RequestParam String sdt,
            @RequestParam String xacNhanMatKhau,
            RedirectAttributes redirect) {

        if (taiKhoanService.getEmail(taiKhoan.getEmail()) != null) {
            redirect.addFlashAttribute("error", "Email đã tồn tại");
            return "redirect:/sign-up";
        }

        if (!taiKhoan.getMatKhau().equals(xacNhanMatKhau)) {
            redirect.addFlashAttribute("error", "Mật khẩu không khớp. Vui lòng nhập lại!");
            return "redirect:/sign-up";
        }

        taiKhoan.setMatKhau(new BCryptPasswordEncoder().encode(taiKhoan.getMatKhau()));
        if (!taiKhoanService.dangKyTaiKhoan(taiKhoan)) {
            redirect.addFlashAttribute("error", "Có lỗi khi đăng ký tài khoản!");
            return "redirect:/sign-up";
        }

        Integer id = taiKhoanService.getID(taiKhoan.getEmail());
        if (id == null) {
            redirect.addFlashAttribute("error", "Không tìm thấy ID tài khoản sau khi đăng ký!");
            return "redirect:/sign-up";
        }

        Volunteer volunteer = new Volunteer();
        volunteer.setMaThanhVien(id);
        volunteer.setHoTen(hoTen);
        volunteer.setSdt(sdt);
        volunteer.setDiaChi("");
        volunteer.setTrangThai("Đã đăng ký");
        volunteer.setNgayDangKy(new Date(System.currentTimeMillis()));
        volunteer.setUrlAvatar("");
        volunteer.setChucVu("Tình nguyện viên");

        if (!volunteerService.addVolunteer(volunteer)) {
            redirect.addFlashAttribute("error", "Có lỗi khi đăng ký thông tin cá nhân!");
            return "redirect:/sign-up";
        }

        redirect.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/sign-in";
    }

    @GetMapping("/sign-in")
    public String showLoginForm(Model model) {
        model.addAttribute("pageTitle", "Đăng nhập");
        model.addAttribute("customCss", "/src/css/template-signin-signup.css");
        model.addAttribute("pageContent", "/WEB-INF/views/signin-signup.jsp");
        return "layout/layoutmaster";
    }

    @PostMapping("/sign-in")
    public String processLogin(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirect) {

        TaiKhoan taiKhoan = taiKhoanService.getEmail(username);

        if (taiKhoan != null && new BCryptPasswordEncoder().matches(password, taiKhoan.getMatKhau())) {
            Volunteer volunteer = volunteerService.getThanhVienDetails(taiKhoan.getMaTaiKhoan());

            String urlAvatar = (volunteer != null && volunteer.getUrlAvatar() != null && !volunteer.getUrlAvatar().trim().isEmpty())
                    ? "/images/uploads" + volunteer.getUrlAvatar()
                    : "/src/images/default-avatar.png";

            session.setAttribute("user", taiKhoan);
            session.setAttribute("urlAvatar", urlAvatar);
            session.setAttribute("loggedInAccount", taiKhoan);
            session.setAttribute("loggedInProfile", volunteer);
            session.setAttribute("isLoggedIn", true);

            return "redirect:/";
        }

        redirect.addFlashAttribute("error", "Sai email hoặc mật khẩu.");
        return "redirect:/sign-in";
    }
}
