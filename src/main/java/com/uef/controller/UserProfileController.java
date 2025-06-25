package com.uef.controller;

import com.uef.model.HoatDong;
import com.uef.model.TaiKhoan;
import com.uef.model.LichSuDangKy;
import com.uef.service.DangKyService;
import com.uef.service.DanhGiaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserProfileController {

    @Autowired
    private DangKyService dangKyService;

    @Autowired
    private DanhGiaService danhGiaService;

    @GetMapping("/history")
    public String showParticipationHistory(HttpSession session, Model model) {
        TaiKhoan currentUser = (TaiKhoan) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/sign-in";
        }

        int maTNV = currentUser.getMaTaiKhoan();

        // Lấy lịch sử đăng ký và hủy đăng ký
        List<LichSuDangKy> lichSuDangKy = dangKyService.getLichSuDangKy(maTNV);
        List<LichSuDangKy> lichSuHuyDangKy = dangKyService.getLichSuHuyDangKy(maTNV);

        // Lấy danh sách các hoạt động đã tham gia
        List<HoatDong> attendedEvents = dangKyService.getAttendedHistory(maTNV);

        // Lấy danh sách các hoạt động đã đăng ký
        List<HoatDong> registeredEvents = dangKyService.getRegisteredEvents(maTNV);

        // Lấy danh sách các hoạt động đã hủy đăng ký
        List<HoatDong> unregisteredEvents = dangKyService.getUnregisteredHistory(maTNV);

        // Lấy danh sách ID các hoạt động đã đánh giá để so sánh
        List<Integer> reviewedEventIds = danhGiaService.getReviewedEventIds(maTNV);

        model.addAttribute("attendedEvents", attendedEvents);
        model.addAttribute("reviewedEventIds", reviewedEventIds);
        model.addAttribute("registeredEvents", registeredEvents);
        model.addAttribute("unregisteredEvents", unregisteredEvents);
        model.addAttribute("lichSuDangKy", lichSuDangKy);
        model.addAttribute("lichSuHuyDangKy", lichSuHuyDangKy);

        model.addAttribute("pageTitle", "Lịch sử tham gia");
        model.addAttribute("customCss", "/src/css/template-custom.css");
        model.addAttribute("pageContent", "/WEB-INF/views/user_history.jsp");
        return "layout/layoutmaster";
    }
}
