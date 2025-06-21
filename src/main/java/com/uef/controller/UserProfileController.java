package com.uef.controller;

import com.uef.model.HoatDong;
import com.uef.model.TaiKhoan;
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

        // Lấy danh sách các hoạt động đã tham gia
        List<HoatDong> attendedEvents = dangKyService.getAttendedHistory(maTNV);

        // Lấy danh sách ID các hoạt động đã đánh giá để so sánh
        List<Integer> reviewedEventIds = danhGiaService.getReviewedEventIds(maTNV);

        model.addAttribute("attendedEvents", attendedEvents);
        model.addAttribute("reviewedEventIds", reviewedEventIds);

        return "user_history"; // Trả về file JSP mới mà chúng ta sẽ tạo ở bước 3
    }
}
