package com.uef.controller;

import com.uef.model.DanhGia;
import com.uef.model.HoatDong;
import com.uef.model.TaiKhoan;
import com.uef.service.DanhGiaService;
import com.uef.service.HoatDongService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/review")
public class DanhGiaController {

    @Autowired
    private DanhGiaService danhGiaService;

    @Autowired
    private HoatDongService hoatDongService;

    @GetMapping("/form")
    public String showReviewForm(@RequestParam("maHoatDong") int maHoatDong, Model model) {
        HoatDong hoatDong = hoatDongService.layHoatDongTheoMa(maHoatDong);
        model.addAttribute("hoatDong", hoatDong);
        model.addAttribute("danhGia", new DanhGia());
        
        
        model.addAttribute("pageTitle", "Viết đánh giá: " + hoatDong.getTenHoatDong());
        model.addAttribute("pageContent", "/WEB-INF/views/review_form.jsp");
        return "layout/layoutmaster";
    }

    @PostMapping("/submit")
    public String submitReview(@ModelAttribute DanhGia danhGia,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        TaiKhoan currentUser = (TaiKhoan) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/sign-in";
        }

        danhGia.setMaTNV(currentUser.getMaTaiKhoan());

        try {
            danhGiaService.submitReview(danhGia);
            redirectAttributes.addFlashAttribute("successMessage", "Cảm ơn bạn đã gửi đánh giá!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gửi đánh giá thất bại: " + e.getMessage());
        }

        // Chuyển hướng về trang chi tiết của hoạt động vừa đánh giá
        return "redirect:/activity/details/" + danhGia.getMaHoatDong();
    }
}
