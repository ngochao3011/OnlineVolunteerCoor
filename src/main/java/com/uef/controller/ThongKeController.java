/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.ThongKe;
import com.uef.service.ThongKeService;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Asus
 */
@Controller
@RequestMapping("/thongke")
public class ThongKeController {

    private final ThongKeService thongKeService;

    @Autowired
    public ThongKeController(ThongKeService thongKeService) {
        this.thongKeService = thongKeService;
    }

    // ================= 1. THỐNG KÊ HOẠT ĐỘNG =================
    @GetMapping("/hoatdong")
    public String hienThiThongKeHoatDong(
            @RequestParam(name = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "status", required = false) String status,
            Model model) {

        Map<String, Integer> thongKeThang = thongKeService.laySoLieuThongKeTheoThang(from, to, status);
        Map<String, Integer> thongKeTrangThai = thongKeService.laySoLieuThongKeTheoTrangThai(from, to, status);
        ThongKe summary = thongKeService.layThongKeTongQuan(from, to, status);

        model.addAttribute("thongKeTheoThang", thongKeThang);
        model.addAttribute("thongKeTheoTrangThai", thongKeTrangThai);
        model.addAttribute("summary", summary);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("status", status);

        model.addAttribute("pageTitle", "Thống Kê Hoạt Động");
        model.addAttribute("customCss", "/src/css/template-custom.css");
        model.addAttribute("pageContent", "/WEB-INF/views/thongke/hoatdong.jsp");
        return "layout/layoutmaster";
    }

    // ================= 2. THỐNG KÊ TÌNH NGUYỆN VIÊN =================
    @GetMapping("/tinhnguyenvien")
    public String hienThiThongKeTNV(
            @RequestParam(name = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,

            @RequestParam(name = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,

            Model model) {

        Map<String, Integer> soTNVTheoHoatDong = thongKeService.layThongKeTNVTheoHoatDong(from, to);
        int tongTNV = thongKeService.layTongTNVThamGia(from, to);
        int tongTNVThangTruoc = thongKeService.layTongTNVThangTruoc();

        double tyLeTang = 0;
        if (tongTNVThangTruoc > 0) {
            tyLeTang = ((double)(tongTNV - tongTNVThangTruoc) / tongTNVThangTruoc) * 100;
        }

        model.addAttribute("soTNVTheoHoatDong", soTNVTheoHoatDong);
        model.addAttribute("tongTNV", tongTNV);
        model.addAttribute("tyLeTang", Math.round(tyLeTang * 10.0) / 10.0); // Làm tròn 1 chữ số thập phân
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        model.addAttribute("pageTitle", "Thống Kê Tình Nguyện Viên");
        model.addAttribute("customCss", "/src/css/template-custom.css");
        model.addAttribute("pageContent", "/WEB-INF/views/thongke/tinhnguyenvien.jsp");
        return "layout/layoutmaster";
    }

    // ================= 3. EXPORT EXCEL - HOẠT ĐỘNG =================
    @GetMapping("/hoatdong/export/excel")
    public void exportExcelHoatDong(
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "status", required = false) String status,
            HttpServletResponse response) {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=thongke-hoatdong.xlsx");

        try {
            thongKeService.exportThongKeHoatDongToExcel(response.getOutputStream(), from, to, status);
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
