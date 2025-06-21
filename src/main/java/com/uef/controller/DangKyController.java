package com.uef.controller;

import com.uef.model.TaiKhoan;
import com.uef.service.DangKyService;
import com.uef.service.HoatDongService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.uef.model.HoatDong;

@Controller
@RequestMapping("/register")
public class DangKyController {

    @Autowired
    private DangKyService dangKyService;

    @Autowired
    private HoatDongService hoatDongService;

  @PostMapping("/add")
       public String register(@RequestParam("maHoatDong") int maHoatDong, HttpSession session, RedirectAttributes redirectAttributes) {
        TaiKhoan currentUser = (TaiKhoan) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/sign-in";
        }
        int maTNV = currentUser.getMaTaiKhoan();

        try {
            dangKyService.registerForEvent(currentUser.getMaTaiKhoan(), maHoatDong);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký hoạt động thành công! Vui lòng chờ điều phối viên xét duyệt.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Đăng ký thất bại: " + e.getMessage());
        }

        return "redirect:/activity";
    }

    @PostMapping("/delete")
    public String unregister(@RequestParam("maHoatDong") int maHoatDong, HttpSession session, RedirectAttributes redirectAttributes) {
        TaiKhoan currentUser = (TaiKhoan) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/sign-in";
        }

        try {
            // Lấy thông tin hoạt động trước khi hủy đăng ký
            HoatDong hoatDong = hoatDongService.layHoatDongTheoMa(maHoatDong);
            String trangThaiTruoc = hoatDong.getTrangThai();
            
            // Thực hiện hủy đăng ký
            dangKyService.unregisterFromEvent(currentUser.getMaTaiKhoan(), maHoatDong);
            
            // Lấy thông tin hoạt động sau khi hủy đăng ký
            hoatDong = hoatDongService.layHoatDongTheoMa(maHoatDong);
            String trangThaiSau = hoatDong.getTrangThai();
            
            // Tạo thông báo
            String message = "Hủy đăng ký thành công.";
            
            // Nếu trạng thái thay đổi, thêm thông tin vào thông báo
            if (!trangThaiTruoc.equals(trangThaiSau)) {
                message += " Hoạt động đã chuyển từ trạng thái '" + trangThaiTruoc + "' sang '" + trangThaiSau + "'.";
            }
            
            redirectAttributes.addFlashAttribute("successMessage", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể hủy đăng ký: " + e.getMessage());
        }

        return "redirect:/activity";
    }
}
