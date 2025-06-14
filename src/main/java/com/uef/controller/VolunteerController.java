package com.uef.controller;

import com.uef.model.TaiKhoan;
import com.uef.model.Volunteer;
import com.uef.service.VolunteerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    private static final int PAGE_SIZE = 100; // Số tình nguyện viên mỗi trang

    // Hiển thị danh sách tình nguyện viên với phân trang
// Chỉ cập nhật phương thức listVolunteers
    @GetMapping
    public String listVolunteers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        int totalVolunteers;
        int currentPage;
        List<Volunteer> volunteers;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 1. Đếm số kết quả trước
            totalVolunteers = volunteerService.countVolunteersByName(keyword.trim());

            // 2. Tính totalPages & currentPage an toàn
            int totalPages = (int) Math.ceil((double) totalVolunteers / PAGE_SIZE);
            currentPage = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));

            // 3. Lấy danh sách theo page
            volunteers = volunteerService.searchByName(keyword.trim(), currentPage, PAGE_SIZE);

            // Gán vào model
            model.addAttribute("searchKeyword", keyword.trim());
            model.addAttribute("totalPages", totalPages);
        } else {
            // Không có keyword, xử lý như cũ
            totalVolunteers = volunteerService.countAllVolunteers();
            int totalPages = (int) Math.ceil((double) totalVolunteers / PAGE_SIZE);
            currentPage = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
            volunteers = volunteerService.getAllVolunteers(currentPage, PAGE_SIZE);
            model.addAttribute("totalPages", totalPages);
        }

        model.addAttribute("volunteers", volunteers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", PAGE_SIZE);

        return "VolunteerList";
    }

    // Hiển thị form thêm mới tình nguyện viên
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        model.addAttribute("taiKhoan", new TaiKhoan());
        return "VolunteerForm";
    }

    // Hiển thị form sửa tình nguyện viên theo ID
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Volunteer volunteer = volunteerService.getVolunteerById(id);
        if (volunteer == null) {
            return "redirect:/volunteer";
        }
        model.addAttribute("volunteer", volunteer);
        model.addAttribute("taiKhoan", new TaiKhoan());
        return "VolunteerForm";
    }

    // Xử lý cập nhật thông tin tình nguyện viên
    @PostMapping("/edit")
    public String updateVolunteer(@ModelAttribute Volunteer volunteer, RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (volunteer.getHoTen() == null || volunteer.getHoTen().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Họ tên không được để trống.");
                return "redirect:/volunteer/edit/" + volunteer.getMaTNV();
            }
            if (volunteer.getSdtDienThoai() != null && !volunteer.getSdtDienThoai().matches("^0\\d{9}$")) {
                redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại phải bắt đầu bằng 0 và có đúng 10 chữ số.");
                return "redirect:/volunteer/edit/" + volunteer.getMaTNV();
            }

            boolean success = volunteerService.updateVolunteer(volunteer);
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật tình nguyện viên thành công.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật tình nguyện viên: Không thể lưu dữ liệu.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/volunteer/edit/" + volunteer.getMaTNV();
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi dữ liệu: Dữ liệu không hợp lệ hoặc vi phạm ràng buộc cơ sở dữ liệu.");
            e.printStackTrace();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/volunteer";
    }

    // Xóa tình nguyện viên theo ID
    @PostMapping("/delete")
    public String deleteVolunteer(
            @RequestParam("maTNV") int maTNV,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "keyword", required = false) String keyword,
            RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = volunteerService.deleteVolunteer(maTNV);
            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", "Xóa tình nguyện viên và tài khoản thành công.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Tình nguyện viên không tồn tại.");
            }
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa vì tài khoản hoặc tình nguyện viên đang được tham chiếu trong dữ liệu khác (ví dụ: điểm danh).");
            e.printStackTrace();
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa: " + e.getMessage());
            e.printStackTrace();
        }
        String redirectUrl = "redirect:/volunteer?page=" + page;
        if (keyword != null && !keyword.trim().isEmpty()) {
            redirectUrl += "&keyword=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        }
        return redirectUrl;
    }

    // Tìm kiếm tình nguyện viên theo tên
    @GetMapping("/search")
    public String searchVolunteers(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {
        return listVolunteers(page, keyword, model);
    }

    // Xuất CSV
    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=tinh_nguyen_vien.csv");

        List<Volunteer> volunteers = volunteerService.getAllVolunteers(1, Integer.MAX_VALUE); // Lấy tất cả để xuất CSV

        PrintWriter writer = response.getWriter();
        writer.println("Mã TNV,Họ tên,SĐT,Địa chỉ,Trạng thái,Ngày đăng ký");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Volunteer v : volunteers) {
            String ngayDangKy = v.getNgayDangKy() != null ? sdf.format(v.getNgayDangKy()) : "";
            writer.printf("%s,%s,%s,%s,%s,%s%n",
                    v.getMaTNV(),
                    v.getHoTen(),
                    v.getSdtDienThoai(),
                    v.getDiaChi(),
                    v.getTrangThai(),
                    ngayDangKy);
        }
        writer.flush();
        writer.close();
    }
}
