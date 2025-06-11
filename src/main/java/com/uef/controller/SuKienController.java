/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.SuKien;
import com.uef.service.SuKienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.*;

@Controller
@RequestMapping("/activity")
public class SuKienController {

    private static final Logger logger = LoggerFactory.getLogger(SuKienController.class);

    @Autowired
    private SuKienService suKienService;

    @GetMapping("")
    public String listSuKien(Model model) {
        try {
            List<SuKien> danhSach = suKienService.layDanhSachSuKien();
            model.addAttribute("danhSachSuKien", danhSach);
            return "activitylist";
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách sự kiện: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể tải danh sách sự kiện");
            return "activity/list";
        }
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("suKien", new SuKien());
        return "activity/add";
    }

    @PostMapping("/add")
    public String addSuKien(@ModelAttribute SuKien suKien, Model model) {
        try {
            suKienService.themSuKien(suKien); // Không set thời gian ở đây, xử lý trong service
            logger.info("Thêm sự kiện thành công: {}", suKien.getTenSuKien());
            return "redirect:/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi thêm sự kiện: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể thêm sự kiện");
            return "activity/add";
        }
    }

    @GetMapping("/edit/{maSuKien}")
    public String showEditForm(@PathVariable int maSuKien, Model model) {
        try {
            SuKien suKien = suKienService.laySuKienTheoMa(maSuKien);
            model.addAttribute("suKien", suKien);
            return "activity/edit";
        } catch (Exception e) {
            logger.error("Lỗi khi lấy sự kiện để chỉnh sửa: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể tải thông tin sự kiện");
            return "redirect:/activity";
        }
    }

    @PostMapping("/edit")
    public String updateSuKien(@ModelAttribute SuKien suKien, Model model) {
        try {
            suKienService.capNhatSuKien(suKien);
            logger.info("Cập nhật sự kiện thành công: {}", suKien.getTenSuKien());
            return "redirect:/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật sự kiện: {}", e.getMessage());
            model.addAttribute("error", "Không thể cập nhật sự kiện");
            return "activity/edit";
        }
    }

    @GetMapping("/delete/{maSuKien}")
    public String deleteSuKien(@PathVariable int maSuKien, Model model) {
        try {
            suKienService.xoaSuKien(maSuKien);
            logger.info("Xóa sự kiện thành công, mã số: {}", maSuKien);
            return "redirect:/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi xóa sự kiện: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể xóa sự kiện");
            return "redirect:/activity";
        }
    }

    @PostMapping("/update")
    public String updateStatus(@RequestParam int maSuKien, @RequestParam String trangThai, Model model) {
        try {
            SuKien suKien = suKienService.laySuKienTheoMa(maSuKien);
            suKien.setTrangThai(trangThai);
            suKienService.capNhatSuKien(suKien);
            logger.info("Cập nhật trạng thái sự kiện thành công, mã số: {}", maSuKien);
            return "redirect:/activity";
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật trạng thái: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể cập nhật trạng thái");
            return "redirect:/activity";
        }
    }
}
