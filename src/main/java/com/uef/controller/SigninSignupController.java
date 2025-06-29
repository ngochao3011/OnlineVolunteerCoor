package com.uef.controller;

import com.uef.model.TaiKhoan;
import com.uef.model.Volunteer;
import com.uef.service.MailService;
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
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;

@Controller
public class SigninSignupController {

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private MailService mailService;

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
            redirect.addFlashAttribute("errMessage", "Email đã tồn tại");
            return "redirect:/sign-up";
        }

        if (!taiKhoan.getMatKhau().equals(xacNhanMatKhau)) {
            redirect.addFlashAttribute("errMessage", "Mật khẩu không khớp. Vui lòng nhập lại!");
            return "redirect:/sign-up";
        }

        taiKhoan.setMatKhau(new BCryptPasswordEncoder().encode(taiKhoan.getMatKhau()));
        if (!taiKhoanService.dangKyTaiKhoan(taiKhoan)) {
            redirect.addFlashAttribute("errMessage", "Có lỗi khi đăng ký tài khoản!");
            return "redirect:/sign-up";
        }

        Integer id = taiKhoanService.getID(taiKhoan.getEmail());
        if (id == null) {
            redirect.addFlashAttribute("errMessage", "Không tìm thấy ID tài khoản sau khi đăng ký!");
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
            redirect.addFlashAttribute("errMessage", "Có lỗi khi đăng ký thông tin cá nhân!");
            return "redirect:/sign-up";
        }

        redirect.addFlashAttribute("success", "Đăng ký thành công!");
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
            RedirectAttributes redirect,
            @RequestParam(value = "redirect", required = false) String redirectUrl) {

        TaiKhoan taiKhoan = taiKhoanService.getEmail(username);

        if (taiKhoan != null && new BCryptPasswordEncoder().matches(password, taiKhoan.getMatKhau())) {
            Volunteer volunteer = volunteerService.getThanhVienDetails(taiKhoan.getMaTaiKhoan());

            String urlAvatar = (volunteer != null && volunteer.getUrlAvatar() != null && !volunteer.getUrlAvatar().trim().isEmpty())
                    ? volunteer.getUrlAvatar()
                    : "/src/images/default-avatar.png";

            // Thiết lập các thuộc tính session
            session.setAttribute("user", taiKhoan);
            session.setAttribute("urlAvatar", urlAvatar);
            session.setAttribute("loggedInAccount", taiKhoan);
            session.setAttribute("loggedInProfile", volunteer);
            session.setAttribute("isLoggedIn", true);

            // Xác thực và chuyển hướng
            if (redirectUrl != null && !redirectUrl.isEmpty() && redirectUrl.startsWith("/")) {
                // Kiểm tra redirectUrl hợp lệ (chỉ cho phép ký tự an toàn)
                if (redirectUrl.matches("^/[a-zA-Z0-9/\\-._]+$")) {
                    return "redirect:" + redirectUrl;
                } else {
                    redirect.addFlashAttribute("error", "URL chuyển hướng không hợp lệ.");
                    return "redirect:/";
                }
            }
            return "redirect:/";
        }

        redirect.addFlashAttribute("errMessage", "Sai email hoặc mật khẩu.");
        return "redirect:/sign-in";
    }

    @PostMapping(value = "/forgot-password", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String forgotPassword(
            @RequestParam("email") String email,
            RedirectAttributes redirect) {

        // Kiểm tra tài khoản tồn tại
        TaiKhoan tk = taiKhoanService.getEmail(email);
        if (tk == null) {
            return "Email không tồn tại trong hệ thống.";
        }

        // Tạo mật khẩu ngẫu nhiên
        String newPassword = RandomStringUtils.randomAlphanumeric(6); // ví dụ: "a8xP2q"

        // Cập nhật mật khẩu mới (có mã hóa nếu cần)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedNew = encoder.encode(newPassword);
        taiKhoanService.updatePassword(tk.getMaTaiKhoan(), encodedNew);
        tk.setMatKhau(encodedNew);

        // Gửi email
        try {
            mailService.sendPassword(tk.getEmail(), newPassword);
        } catch (Exception e) {
            return "Không thể gửi email. Vui lòng thử lại sau.";
        }

        return "Mật khẩu mới đã được gửi đến email của bạn.";
    }
}
