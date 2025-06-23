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
import com.uef.util.QRGenerator;
import java.io.File;
import java.util.ArrayList;
import java.time.LocalDate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                model.addAttribute("pageTitle", "Danh sách sự kiện");
                model.addAttribute("customCss", "/src/css/template-custom.css");
                model.addAttribute("pageContent", "/WEB-INF/views/activitylist.jsp");
                return "layout/layoutmaster";
            } else {
                model.addAttribute("pageTitle", "Danh sách sự kiện");
                model.addAttribute("customCss", "/src/css/template-custom.css");
                model.addAttribute("pageContent", "/WEB-INF/views/activitylist_volunteer.jsp");
                return "layout/layoutmaster";
            }

        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách sự kiện: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể tải danh sách sự kiện");
            model.addAttribute("pageTitle", "Danh sách sự kiện");
            model.addAttribute("customCss", "/src/css/template-custom.css");
            model.addAttribute("pageContent", "/WEB-INF/views/activitylist.jsp");
            return "layout/layoutmaster";
        }
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("hoatDong", new HoatDong());
        model.addAttribute("pageTitle", "Thêm Hoạt Động Mới");
        model.addAttribute("pageContent", "/WEB-INF/views/activity/add.jsp");
        return "layout/layoutmaster";
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
            model.addAttribute("pageTitle", "Thêm Hoạt Động Mới");
            model.addAttribute("pageContent", "/WEB-INF/views/activity/add.jsp");
            return "layout/layoutmaster";
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
            model.addAttribute("pageTitle", "Chi tiết Hoạt động - " + hoatDong.getTenHoatDong());
            model.addAttribute("customCss", "/src/css/template-custom.css");
            model.addAttribute("pageContent", "/WEB-INF/views/activity/details.jsp");
            return "layout/layoutmaster";
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
            model.addAttribute("pageTitle", "Chỉnh Sửa Hoạt Động");
            model.addAttribute("pageContent", "/WEB-INF/views/activity/edit.jsp");
            return "layout/layoutmaster";
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
            model.addAttribute("pageTitle", "Chỉnh Sửa Hoạt Động");
            model.addAttribute("pageContent", "/WEB-INF/views/activity/edit.jsp");
            return "layout/layoutmaster";
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

    @GetMapping("/checkin/{maHoatDong}")
    public String showQRCode(@PathVariable int maHoatDong, Model model, HttpSession session) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");
        if (user == null) {
            return "redirect:/sign-in";
        }

        // Link xác nhận điểm danh
        String qrData = "http://localhost:8080/OnlineVolunteerCoor/activity/confirm-checkin/" + maHoatDong;

        // Tạo ảnh QR nội bộ (tên file: qr_{hoatDongId}.png)
        String fileName = "qr_" + maHoatDong + ".png";
        String dirPath = "D:/uploads/qrcodes/";
        String filePath = dirPath + fileName;

        File qrDir = new File(dirPath);
        if (!qrDir.exists()) {
            qrDir.mkdirs(); // tạo cả chuỗi thư mục nếu cần
        }

        try {
            QRGenerator.generateQRCodeImage(qrData, filePath);
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tạo mã QR");
        }

        model.addAttribute("qrImage", "/images/uploads/qrcodes/" + fileName);
        model.addAttribute("qrLink", qrData);
        model.addAttribute("pageTitle", "Điểm danh Hoạt Động " + maHoatDong);
        model.addAttribute("pageContent", "/WEB-INF/views/activity/checkin.jsp");
        return "layout/layoutmaster";
    }

    @GetMapping("/confirm-checkin/{maHoatDong}")
    public String confirmCheckin(@PathVariable("maHoatDong") int maHoatDong,
            Model model,
            HttpSession session,
            RedirectAttributes redirect) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");
        if (user == null) {
            return "redirect:/sign-in";
        }

        
        model.addAttribute("pageTitle", "Xác nhận điểm danh hoạt động " + maHoatDong);
        model.addAttribute("pageContent", "/WEB-INF/views/activity/confirm-checkin.jsp");
        return "layout/layoutmaster";
    }

}
