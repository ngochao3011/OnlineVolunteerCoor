/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.HoatDong;
import com.uef.service.HoatDongService;
import com.uef.service.DangKyService;
import com.uef.service.DanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.*;
import jakarta.servlet.http.HttpSession;
import com.uef.model.TaiKhoan;
import java.util.ArrayList;
import java.time.LocalDate;

@Controller
@RequestMapping("/activity")
public class HoatDongController {

    private static final Logger logger = LoggerFactory.getLogger(HoatDongController.class);

    @Autowired
    private HoatDongService hoatDongService;

    @Autowired
    private DangKyService dangKyService;

    @Autowired
    private DanhGiaService danhGiaService;

    @GetMapping("")
     public String listHoatDong(@RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "trangThai", required = false) String trangThai,
            @RequestParam(name = "page", defaultValue = "1") int page,
            HttpSession session,
            Model model) {
        try {
            TaiKhoan currentUser = (TaiKhoan) session.getAttribute("user");
            if (currentUser == null) {
                return "redirect:/sign-in";
            }
            List<Integer> registeredEventIds = dangKyService.getRegisteredEventIds(currentUser.getMaTaiKhoan());
            model.addAttribute("registeredEventIds", registeredEventIds);

            List<HoatDong> danhSachHoatDong = hoatDongService.timKiemVaPhanTrang(keyword, location, trangThai, page);
            int totalItems = hoatDongService.demTongSoHoatDongTimKiem(keyword, location, trangThai);
            int totalPages = (int) Math.ceil((double) totalItems / HoatDongService.PAGE_SIZE);

            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }

            model.addAttribute("danhSachHoatDong", danhSachHoatDong);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("keyword", keyword);
            model.addAttribute("location", location);
            model.addAttribute("trangThai", trangThai);
            System.out.println(currentUser.getQuyenHan());
            if ("Điều phối viên".equals(currentUser.getQuyenHan())) {
                return "activitylist";
            } else {
                return "activitylist_volunteer";
            }

        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách sự kiện: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể tải danh sách sự kiện");
            return "activitylist";
        }
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("hoatDong", new HoatDong());
        return "activity/add";
    }

    @PostMapping("/add")
    public String addHoatDong(@ModelAttribute HoatDong hoatDong, Model model) {
        try {
            hoatDongService.themHoatDong(hoatDong); // Không set thời gian ở đây, xử lý trong service
            logger.info("Thêm hoạt động thành công: {}", hoatDong.getTenHoatDong());
            return "redirect:/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi thêm hoạt động: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể thêm hoạt động");
            return "activity/add";
        }
    }

    
    @GetMapping("/details/{maHoatDong}")
    public String viewHoatDongDetails(@PathVariable int maHoatDong, Model model, HttpSession session) {
        try {
                HoatDong hoatDong = hoatDongService.layHoatDongTheoMa(maHoatDong);
                model.addAttribute("hoatDong", hoatDong);

            TaiKhoan currentUser = (TaiKhoan) session.getAttribute("user");
            if (currentUser != null && "Tình nguyện viên".equals(currentUser.getQuyenHan())) {
                int maTNV = currentUser.getMaTaiKhoan();
                boolean daDuyetThamGia = danhGiaService.canReview(maTNV, maHoatDong);
                boolean daDanhGia = danhGiaService.hasReviewed(maTNV, maHoatDong);

                // Chỉ có thể đánh giá nếu sự kiện đã kết thúc, đã được duyệt tham gia và chưa đánh giá
                boolean coTheDanhGia = "Đã kết thúc".equals(hoatDong.getTrangThai()) && daDuyetThamGia && !daDanhGia;

                model.addAttribute("coTheDanhGia", coTheDanhGia);
                model.addAttribute("daDanhGia", daDanhGia);
            }
            return "activity/details";
        } catch (Exception e) {
            logger.error("Lỗi khi lấy chi tiết hoạt động: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể tải thông tin hoạt động");
            return "redirect:/activity";
        }
    }

    @GetMapping("/edit/{maHoatDong}")
    public String showEditForm(@PathVariable int maHoatDong, Model model) {
        try {
            HoatDong hoatDong = hoatDongService.layHoatDongTheoMa(maHoatDong);
            model.addAttribute("hoatDong", hoatDong);
            return "activity/edit";
        } catch (Exception e) {
            logger.error("Lỗi khi lấy hoạt động để chỉnh sửa: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể tải thông tin hoạt động");
            return "redirect:/activity";
        }
    }

    @PostMapping("/edit")
    public String updateHoatDong(@ModelAttribute HoatDong hoatDong, Model model) {
        try {
            hoatDongService.capNhatHoatDong(hoatDong);
            logger.info("Cập nhật hoạt động thành công: {}", hoatDong.getTenHoatDong());
            return "redirect:/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật hoạt động: {}", e.getMessage());
            model.addAttribute("error", "Không thể cập nhật hoạt động");
            return "activity/edit";
        }
    }

    @GetMapping("/delete/{maHoatDong}")
    public String deleteHoatDong(@PathVariable int maHoatDong, Model model) {
        try {
            hoatDongService.xoaHoatDong(maHoatDong);
            logger.info("Xóa hoạt động thành công, mã số: {}", maHoatDong);
            return "redirect:/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi xóa hoạt động: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể xóa hoạt động");
            return "redirect:/activity";
        }
    }

    @PostMapping("/update")
    public String updateStatus(@RequestParam int maHoatDong, @RequestParam String trangThai, Model model) {
        try {
            HoatDong hoatDong = hoatDongService.layHoatDongTheoMa(maHoatDong);
            hoatDong.setTrangThai(trangThai);
            hoatDongService.capNhatHoatDong(hoatDong);
            logger.info("Cập nhật trạng thái hoạt động thành công, mã số: {}", maHoatDong);
            return "redirect:/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật trạng thái: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể cập nhật trạng thái");
            return "redirect:/activity";
        }
    }
} 