/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.*;
import com.uef.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.*;
import jakarta.servlet.http.HttpSession;
import com.uef.util.QRGenerator;
import java.io.File;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("")
public class HoatDongController {

    private static final Logger logger = LoggerFactory.getLogger(HoatDongController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HoatDongService hoatDongService;

    @Autowired
    private DangKyService dangKyService;

    @Autowired
    private DanhGiaService danhGiaService;

    @Autowired
    private LichSuHoatDongService lichSuHoatDongService;
    
    @Autowired
    private DuyetDangKyService duyetDangKyService;
    
    @Autowired
    private DiemDanhService diemDanhService;

    @GetMapping("/activity")
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
                model.addAttribute("pageContent", "/WEB-INF/views/admin/activitylist.jsp");
                return "layout/layoutadmin";
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
            model.addAttribute("pageContent", "/WEB-INF/views/activitylist_volunteer.jsp");
            return "layout/layoutmaster";
        }
    }
    
    @GetMapping("/admin/activity")
    public String listHoatDongAdmin(@RequestParam(name = "keyword", required = false) String keyword,
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
                model.addAttribute("pageContent", "/WEB-INF/views/admin/activitylist.jsp");
                return "layout/layoutadmin";
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
            model.addAttribute("pageContent", "/WEB-INF/views/admin/activitylist.jsp");
            return "layout/layoutadmin";
        }
    }

    @GetMapping("/admin/activity/add")
    public String showAddForm(Model model) {
        model.addAttribute("hoatDong", new HoatDong());
        model.addAttribute("pageTitle", "Thêm Hoạt Động Mới");
        model.addAttribute("pageContent", "/WEB-INF/views/admin/activity/add.jsp");
        return "layout/layoutadmin";
    }

    @PostMapping("/admin/activity/add")
    public String addHoatDong(@ModelAttribute HoatDong hoatDong, Model model) {
        try {
            hoatDongService.themHoatDong(hoatDong); // Không set thời gian ở đây, xử lý trong service
            logger.info("Thêm hoạt động thành công: {}", hoatDong.getTenHoatDong());
            return "redirect:/admin/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi thêm hoạt động: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể thêm hoạt động");
            model.addAttribute("pageTitle", "Thêm Hoạt Động Mới");
            model.addAttribute("pageContent", "/WEB-INF/views/admin/activity/add.jsp");
            return "layout/layoutadmin";
        }
    }

    @GetMapping("/activity/details/{maHoatDong}")
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

    @GetMapping("/admin/activity/edit/{maHoatDong}")
    public String showEditForm(@PathVariable int maHoatDong, Model model) {
        try {
            HoatDong hoatDong = hoatDongService.layHoatDongTheoMa(maHoatDong);
            model.addAttribute("hoatDong", hoatDong);
            model.addAttribute("pageTitle", "Chỉnh Sửa Hoạt Động");
            model.addAttribute("pageContent", "/WEB-INF/views/admin/activity/edit.jsp");
            return "layout/layoutadmin";
        } catch (Exception e) {
            logger.error("Lỗi khi lấy hoạt động để chỉnh sửa: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể tải thông tin hoạt động");
            return "redirect:/admin/activity";
        }
    }

    @PostMapping("/admin/activity/edit")
    public String updateHoatDong(@ModelAttribute HoatDong hoatDong, Model model) {
        try {
            hoatDongService.capNhatHoatDong(hoatDong);
            logger.info("Cập nhật hoạt động thành công: {}", hoatDong.getTenHoatDong());
            return "redirect:/admin/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật hoạt động: {}", e.getMessage());
            model.addAttribute("error", "Không thể cập nhật hoạt động");
            model.addAttribute("pageTitle", "Chỉnh Sửa Hoạt Động");
            model.addAttribute("pageContent", "/WEB-INF/views/admin/activity/edit.jsp");
            return "layout/layoutadmin";
        }
    }

    @GetMapping("/admin/activity/delete")
    public String deleteHoatDong(@RequestParam("maHoatDong") Integer maHoatDong, RedirectAttributes redirectAttributes) {
        if (maHoatDong == null || maHoatDong <= 0) {
            logger.warn("Invalid maHoatDong: {}", maHoatDong);
            redirectAttributes.addFlashAttribute("errorMessage", "Mã hoạt động không hợp lệ.");
            return "redirect:/admin/activity?page=1";
        }

        String checkSql = "SELECT trangThai, (SELECT COUNT(*) FROM [DANGKYTHAMGIA] WHERE maHoatDong = ?) AS dangKyCount "
                + "FROM [HOATDONG] WHERE maHoatDong = ?";
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(checkSql, maHoatDong, maHoatDong);
            String trangThai = (String) result.get("trangThai");
            int dangKyCount = ((Number) result.get("dangKyCount")).intValue();

            if ("Đang hoạt động".equals(trangThai)) {
                logger.warn("Cannot delete HOATDONG with maHoatDong={} due to active status", maHoatDong);
                redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa hoạt động vì hoạt động đang diễn ra.");
                return "redirect:/admin/activity?page=1";
            }

            if ("Sắp diễn ra".equals(trangThai) && dangKyCount > 0) {
                logger.warn("Cannot delete HOATDONG with maHoatDong={} due to {} registrations", maHoatDong, dangKyCount);
                redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa hoạt động vì có " + dangKyCount + " tình nguyện viên đã đăng ký.");
                return "redirect:/admin/activity?page=1";
            }

            // Xóa các bản ghi liên quan
            jdbcTemplate.update("DELETE FROM [DANGKYTHAMGIA] WHERE maHoatDong = ?", maHoatDong);
            jdbcTemplate.update("DELETE FROM [DIEMDANH] WHERE maHoatDong = ?", maHoatDong);
            jdbcTemplate.update("DELETE FROM [PHIEUDIEMDANH] WHERE maHoatDong = ?", maHoatDong);
            jdbcTemplate.update("DELETE FROM [PHIEUDANHGIA] WHERE maHoatDong = ?", maHoatDong);
            jdbcTemplate.update("DELETE FROM [HINHANH] WHERE maHoatDong = ?", maHoatDong);
            jdbcTemplate.update("DELETE FROM [LICHSUHOATDONG] WHERE maHoatDong = ?", maHoatDong);

            // Xóa HOATDONG
            int rowsAffected = jdbcTemplate.update("DELETE FROM [HOATDONG] WHERE maHoatDong = ?", maHoatDong);
            if (rowsAffected > 0) {
                logger.info("Deleted HOATDONG with maHoatDong={}", maHoatDong);
                redirectAttributes.addFlashAttribute("successMessage", "Xóa hoạt động thành công!");
            } else {
                logger.warn("No HOATDONG found with maHoatDong={}", maHoatDong);
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy hoạt động để xóa.");
            }
        } catch (org.springframework.jdbc.BadSqlGrammarException e) {
            logger.error("SQL error deleting HOATDONG with maHoatDong={}: {}", maHoatDong, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi cú pháp SQL hoặc cấu trúc cơ sở dữ liệu.");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            logger.error("Data integrity violation deleting HOATDONG with maHoatDong={}: {}", maHoatDong, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa hoạt động do còn dữ liệu liên quan.");
        } catch (Exception e) {
            logger.error("Unexpected error deleting HOATDONG with maHoatDong={}: {}", maHoatDong, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi không xác định khi xóa hoạt động: " + e.getMessage());
        }
        return "redirect:/admin/activity?page=1";
    }

    @PostMapping("/admin/activity/update")
    public String updateStatus(@RequestParam int maHoatDong, @RequestParam String trangThai, Model model) {
        try {
            HoatDong hoatDong = hoatDongService.layHoatDongTheoMa(maHoatDong);
            hoatDong.setTrangThai(trangThai);
            hoatDongService.capNhatHoatDong(hoatDong);
            logger.info("Cập nhật trạng thái hoạt động thành công, mã số: {}", maHoatDong);
            return "redirect:/admin/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật trạng thái: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể cập nhật trạng thái");
            return "redirect:/admin/activity";
        }
    }

    @GetMapping("/admin/activity/checkin/{maHoatDong}")
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
        model.addAttribute("pageContent", "/WEB-INF/views/admin/activity/checkin.jsp");
        return "layout/layoutadmin";
    }

    @GetMapping("/activity/confirm-checkin/{maHoatDong}")
    public String confirmCheckin(@PathVariable("maHoatDong") int maHoatDong,
            Model model,
            HttpSession session,
            RedirectAttributes redirect) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");
        if (user == null) {
            return "redirect:/sign-in";
        }
        if ("Điều phối viên".equals(user.getQuyenHan())) {
            redirect.addFlashAttribute("error", "Bạn không có trong danh sách điểm danh.");
            return "redirect:/activity";
        }

        HoatDong hoatDong = hoatDongService.layHoatDongTheoMa(maHoatDong);
        if (hoatDong == null) {
            redirect.addFlashAttribute("error", "Hoạt động không tồn tại.");
            return "redirect:/activity";
        }
        model.addAttribute("hoatDong", hoatDong);
        model.addAttribute("pageTitle", "Xác nhận điểm danh hoạt động " + maHoatDong);
        model.addAttribute("pageContent", "/WEB-INF/views/activity/confirm-checkin.jsp");
        return "layout/layoutmaster";
    }
    
    @PostMapping("/activity/confirm-checkin")
    public String confirmCheckin(@RequestParam("maHoatDong") int maHoatDong,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }
        
        if ("Điều phối viên".equals(user.getQuyenHan())) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có trong danh sách điểm danh.");
            return "redirect:/activity";
        }
        
        // Check duyet dang ky
        if(!duyetDangKyService.checkDangKy(maHoatDong, maHoatDong)){
            redirectAttributes.addFlashAttribute("error", "Bạn chưa đăng ký hoạt động hoặc chưa được duyệt.");
            return "redirect:/activity";
        }
        // Check diem danh
        if(diemDanhService.checkDiemDanh(maHoatDong, maHoatDong)){
            redirectAttributes.addFlashAttribute("error", "Bạn đã điểm danh.");
            return "redirect:/activity";
        }

        boolean success = diemDanhService.checkin(user.getMaTaiKhoan(), maHoatDong);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Điểm danh thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi điểm danh.");
        }
        return "redirect:/activity";
    }

    @GetMapping("admin/activity/history")
    public String showHistory(@RequestParam(required = false) Integer maHoatDong, Model model, HttpSession session) {
        try {
            logger.info("Truy cập lịch sử với maHoatDong: {}", maHoatDong);
            if (session.getAttribute("user") == null) {
                return "redirect:/sign-in";
            }

            // Lấy toàn bộ lịch sử từ LICHSUHOATDONG
            List<LichSuHoatDong> lichSuHoatDongs = lichSuHoatDongService.layTatCaLichSu();
            logger.info("Số lượng lịch sử lấy được: {}", lichSuHoatDongs.size());
            model.addAttribute("lichSuHoatDongs", lichSuHoatDongs);

            model.addAttribute("pageTitle", "Lịch Sử Hoạt Động");
            model.addAttribute("customCss", "/src/css/template-custom.css");
            model.addAttribute("pageContent", "/WEB-INF/views/activity/history.jsp");
            return "layout/layoutadmin";
        } catch (Exception e) {
            logger.error("Lỗi khi lấy lịch sử hoạt động: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể tải dữ liệu");
            return "history"; // Quay lại trang chính
        }
    }

}
