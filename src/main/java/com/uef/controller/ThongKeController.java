/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

/**
 *
 * @author Asus
 */

import com.uef.model.ThongKe;
import com.uef.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

@Controller
public class ThongKeController {

    private final ThongKeService thongKeService;

    @Autowired
    public ThongKeController(ThongKeService thongKeService) {
        this.thongKeService = thongKeService;
    }

    @GetMapping("/thongke")
    public String hienThiThongKe(
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "status", required = false) String status,
            Model model) {

        // Gửi dữ liệu thống kê theo tháng và trạng thái
        Map<String, Integer> thongKeThang = thongKeService.laySoLieuThongKeTheoThang();
        Map<String, Integer> thongKeTrangThai = thongKeService.laySoLieuThongKeTheoTrangThai();

        ThongKe summary = thongKeService.layThongKeTongQuan();

        model.addAttribute("thongKeTheoThang", thongKeThang);
        model.addAttribute("thongKeTheoTrangThai", thongKeTrangThai);
        model.addAttribute("summary", summary);

        // Gửi thêm filter để giữ lại form sau submit
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("status", status);

        return "thongke";
    }
}
