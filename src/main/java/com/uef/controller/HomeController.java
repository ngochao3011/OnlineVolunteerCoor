package com.uef.controller;

import com.uef.model.HoatDong;
import com.uef.model.TaiKhoan;
import com.uef.model.Volunteer;
import com.uef.service.HoatDongService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private HoatDongService hoatDongService;

    private void addLoginInfoToModel(Model model, HttpSession session) {
        TaiKhoan loggedInAccount = (TaiKhoan) session.getAttribute("loggedInAccount");
        Volunteer loggedInProfile = (Volunteer) session.getAttribute("loggedInProfile");

        model.addAttribute("loggedInAccount", loggedInAccount);
        model.addAttribute("loggedInProfile", loggedInProfile);
        model.addAttribute("isLoggedIn", loggedInAccount != null && loggedInProfile != null);
    }

    @RequestMapping("/")
    public String home(Model model, HttpSession session) {
        try {
            // Lấy 3 hoạt động nổi bật
            List<HoatDong> danhSachHoatDong = hoatDongService.layHoatDongNoiBat();
            model.addAttribute("danhSachHoatDong", danhSachHoatDong);
            System.out.println("Số hoạt động truyền vào homepage: " + danhSachHoatDong.size());

            model.addAttribute("pageTitle", "Online Volunteer Coor");
            model.addAttribute("pageContent", "/WEB-INF/views/homepage.jsp");
            addLoginInfoToModel(model, session);

            return "layout/layoutmaster";
        } catch (Exception e) {
            System.err.println("Lỗi khi tải trang chủ: " + e.getMessage());
            model.addAttribute("error", "Không thể tải danh sách hoạt động");
            model.addAttribute("pageTitle", "Online Volunteer Coor");
            model.addAttribute("pageContent", "/WEB-INF/views/homepage.jsp");
            return "layout/layoutmaster";
        }
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
