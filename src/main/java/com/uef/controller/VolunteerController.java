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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    // Hiển thị danh sách tình nguyện viên
    @GetMapping
    public String listVolunteers(Model model) {
        List<Volunteer> list = volunteerService.getAllVolunteers();
        list.forEach(v -> System.out.println(
                "Mã TNV: " + v.getMaTNV()
                + ", Họ Tên: " + v.getHoTen()
                + ", SĐT: " + v.getSdtDienThoai()
                + ", Địa Chỉ: " + v.getDiaChi()
                + ", Trạng Thái: " + v.getTrangThai()
                + ", Ngày Đăng Ký: " + v.getNgayDangKy()
        ));
        model.addAttribute("volunteers", list);
        return "VolunteerList";
    }

    // Hiển thị form thêm mới tình nguyện viên
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        model.addAttribute("taiKhoan", new TaiKhoan());
        return "VolunteerForm";
    }

    // Xử lý thêm mới tình nguyện viên
    @PostMapping("/add")
    public String addVolunteer(
            @RequestParam("email") String email,
            @RequestParam("matKhau") String matKhau,
            @RequestParam("sdtDienThoai") String sdt,
            @ModelAttribute("volunteer") Volunteer volunteer,
            RedirectAttributes redirectAttributes) {

        try {
            // Tạo đối tượng tài khoản mới
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setEmail(email);
            taiKhoan.setMatKhau(matKhau);
            taiKhoan.setSdt(sdt);  // bạn lấy riêng ra đúng rồi
            taiKhoan.setQuyenHan("Tình nguyện viên");

            // Nếu chưa có ngày đăng ký thì set ngày hiện tại
            if (volunteer.getNgayDangKy() == null) {
                volunteer.setNgayDangKy(new Date());
            }

            // Gọi service xử lý
            volunteerService.addVolunteer(taiKhoan, volunteer);

            // Thành công
            redirectAttributes.addFlashAttribute("successMessage", "Thêm tình nguyện viên thành công.");
            return "redirect:/volunteer";

        } catch (IllegalArgumentException e) {
            // Xử lý lỗi nghiệp vụ (VD: email trùng)
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/volunteer/add";
        } catch (Exception e) {
            // Xử lý lỗi hệ thống (VD: NullPointerException, lỗi DB)
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra. Vui lòng thử lại.");
            return "redirect:/volunteer/add";
        }
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
        boolean success = volunteerService.updateVolunteer(volunteer);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật tình nguyện viên thành công.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật tình nguyện viên.");
        }
        return "redirect:/volunteer";
    }

    // Xóa tình nguyện viên theo ID
    @PostMapping("/delete") // Sửa từ @GetMapping sang @PostMapping
    public String deleteVolunteer(@RequestParam("maTNV") int maTNV, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = volunteerService.deleteVolunteer(maTNV);
            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", "Xóa tình nguyện viên thành công.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Tình nguyện viên không tồn tại.");
            }
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa vì tình nguyện viên đã tham gia điểm danh.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa tình nguyện viên: " + e.getMessage());
        }
        return "redirect:/volunteer";
    }

    // Tìm kiếm tình nguyện viên theo tên
    @GetMapping("/search")
    public String searchVolunteers(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Volunteer> result;
        if (keyword == null || keyword.trim().isEmpty()) {
            result = volunteerService.getAllVolunteers();
        } else {
            result = volunteerService.searchByName(keyword);
            if (result.isEmpty()) {
                model.addAttribute("errorMessage", "Không tìm thấy tình nguyện viên phù hợp.");
                result = volunteerService.getAllVolunteers();
                keyword = null;
            }
        }
        model.addAttribute("volunteers", result);
        model.addAttribute("searchKeyword", keyword);
        return "VolunteerList";
    }

    // Xuất CSV
    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=tinh_nguyen_vien.csv");

        List<Volunteer> volunteers = volunteerService.getAllVolunteers();

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
