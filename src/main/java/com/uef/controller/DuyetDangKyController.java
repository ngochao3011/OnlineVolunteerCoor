/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.DuyetDangKy;
import com.uef.service.DuyetDangKyService;
import com.uef.service.MailService;
import jakarta.servlet.http.HttpSession;
import static java.awt.SystemColor.info;
import java.util.List;
import java.util.Map;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/activity/approve")
public class DuyetDangKyController {

    private static final Logger logger = LoggerFactory.getLogger(DuyetDangKyController.class);

    @Autowired
    private DuyetDangKyService duyetDangKyService;

    @Autowired
    private MailService mailService;

    @GetMapping
    public String showApprovePage(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "trangThai", required = false) String trangThai,
            @RequestParam(defaultValue = "1") int page, Model model) {
        List<DuyetDangKy> duyetDangKyList = duyetDangKyService.getDanhSachDangKyCanDuyet(page, keyword, location, trangThai);
        int totalRecords = duyetDangKyService.getTongSoDangKyCanDuyet(keyword, location, trangThai);
        int totalPages = (int) Math.ceil((double) totalRecords / 15);

        if (page > totalPages && totalPages > 0) {
            page = totalPages;
            logger.warn("Page {} exceeds totalPages {}, resetting to {}", page, totalPages, totalPages);
        } else if (page < 1) {
            page = 1;
            logger.warn("Page {} is less than 1, resetting to 1", page);
        }

        // Thêm thuộc tính isUpcoming (giả định trạng thái "Sắp diễn ra" dựa trên dữ liệu)
        boolean isUpcoming = duyetDangKyList.stream().anyMatch(dk -> dk.getHoatDong() != null && "Sắp diễn ra".equals(dk.getHoatDong().getTrangThai()));
        model.addAttribute("isUpcoming", isUpcoming);

        logger.info("Request for page: {}, totalRecords: {}, totalPages: {}, duyetDangKyList.size: {}",
                page, totalRecords, totalPages, duyetDangKyList != null ? duyetDangKyList.size() : 0);
        model.addAttribute("duyetDangKyList", duyetDangKyList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword != null ? keyword : "");
        model.addAttribute("location", location != null ? location : "");
        model.addAttribute("trangThai", trangThai != null ? trangThai : "");
        model.addAttribute("pageTitle", "Danh sách đăng ký cần duyệt");
        model.addAttribute("pageContent", "/WEB-INF/views/approve/list.jsp");
        return "layout/layoutadmin";
    }

    @PostMapping("/approve")
    public String approveDangKy(@RequestParam int maDDK, @RequestParam(required = false) String ghiChu) {
        duyetDangKyService.capNhatTrangThaiDuyet(maDDK, "Đã duyệt", ghiChu != null ? ghiChu : "");
        return "redirect:/activity/approve";
    }

    @GetMapping("/test-mail")
    @ResponseBody
    public String testMail() {
        mailService.sendTestMail("haoln21@uef.edu.vn");
        return "Đã gửi mail";
    }

    @PostMapping("/update")
    public String updateDuyetDangKy(
            @RequestParam("maDDK") int maDDK,
            @RequestParam("trangThaiDuyet") String trangThaiDuyet,
            @RequestParam(value = "ghiChu", required = false) String ghiChu,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "trangThai", required = false) String trangThaiFilter,
            RedirectAttributes redirectAttributes) {

        logger.info("Updating maDDK: {}, trangThaiDuyet: {}, ghiChu: {}", maDDK, trangThaiDuyet, ghiChu);
        duyetDangKyService.capNhatTrangThaiDuyet(maDDK, trangThaiDuyet, ghiChu != null ? ghiChu : "");

        // Gửi mail
        Map<String, Object> mailInfo = duyetDangKyService.getThongTinMail(maDDK);
        String mailTo = (String) mailInfo.get("email");
        String hoTen = (String) mailInfo.get("hoTen");
        String tenHoatDong = (String) mailInfo.get("tenHoatDong");
        mailService.sendApprovedEmail(mailTo, hoTen, tenHoatDong);
        // Thêm thông báo thành công
        redirectAttributes.addFlashAttribute("message", "Cập nhật và gửi mail thông báo thành công!");
        
        // Chuyển hướng về trang hiện tại với các tham số lọc
        String redirectUrl = "/admin/activity/approve?page=" + (page != null ? page : 1);
        if (keyword != null && !keyword.isEmpty()) {
            redirectUrl += "&keyword=" + keyword;
        }
        if (location != null && !location.isEmpty()) {
            redirectUrl += "&location=" + location;
        }
        if (trangThaiFilter != null && !trangThaiFilter.isEmpty()) {
            redirectUrl += "&trangThai=" + trangThaiFilter;
        }
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/reject")
    public String rejectDangKy(@RequestParam int maDDK, @RequestParam(required = false) String ghiChu) {
        duyetDangKyService.capNhatTrangThaiDuyet(maDDK, "Từ chối", ghiChu != null ? ghiChu : "");
        return "redirect:/activity/approve";
    }
}
