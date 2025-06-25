/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.ThongKe;
import com.uef.service.ThongKeService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
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

    @Autowired
    private ThongKeService thongKeService;

    // ================== 1. THỐNG KÊ HOẠT ĐỘNG ==================
    @GetMapping("/hoatdong")
    public String hienThiThongKeHoatDong(
            @RequestParam(name = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "status", required = false) String status,
            Model model) {

        // Summary cards: theo trạng thái biểu đồ
        int soDaKetThuc = thongKeService.getSoDaKetThuc(from, to);
        int soDangHoatDong = thongKeService.getSoDangHoatDong(from, to);
        int soSapDienRa = thongKeService.getSoSapDienRa(from, to);
        int tongHoatDong = soDaKetThuc + soDangHoatDong + soSapDienRa;

        model.addAttribute("soDaKetThuc", soDaKetThuc);
        model.addAttribute("soDangHoatDong", soDangHoatDong);
        model.addAttribute("soSapDienRa", soSapDienRa);
        model.addAttribute("tongHoatDong", tongHoatDong);

        // Tổng hợp cho biểu đồ
        ThongKe thongKe = thongKeService.layThongKeTongQuan(from, to, status);

        // Biểu đồ line: theo tháng
        Map<String, Integer> thongKeThang = thongKeService.laySoLieuThongKeTheoThang(from, to, status);

        // Biểu đồ tròn: theo trạng thái
        Map<String, Integer> thongKeTrangThai = thongKeService.laySoLieuThongKeTheoTrangThai(from, to, status);

        model.addAttribute("thongKe", thongKe);
        model.addAttribute("thongKeTheoThang", thongKeThang);
        model.addAttribute("thongKeTheoTrangThai", thongKeTrangThai);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("status", status);

        return "thongke/hoatdong"; // /WEB-INF/views/thongke/hoatdong.jsp
    }

    //---xuất csv--
    @GetMapping("/export/csv")
    public void exportCSVThongKe(
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "status", required = false) String status,
            HttpServletResponse response) throws IOException {

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=thongke_hoatdong.csv");

        List<ThongKe> hoatDongs = thongKeService.getDanhSachHoatDong(from, to, status);

        PrintWriter writer = response.getWriter();
        writer.println("Mã hoạt động,Tên hoạt động,Ngày bắt đầu,Ngày kết thúc,Địa điểm,Trạng thái");

        for (ThongKe hd : hoatDongs) {
            String ngayBD = hd.getNgayBatDau() != null ? hd.getNgayBatDau().toString() : "";
            String ngayKT = hd.getNgayKetThuc() != null ? hd.getNgayKetThuc().toString() : "";

            writer.printf("%s,%s,%s,%s,%s,%s%n",
                    hd.getMaHoatDong(),
                    hd.getTenHoatDong(),
                    ngayBD,
                    ngayKT,
                    hd.getDiaDiem(),
                    hd.getTrangThaiHoatDong());
        }

        writer.flush();
        writer.close();
    }

    //================== 2. THỐNG KÊ TNV THEO HOẠT ĐỘNG ==================
    @GetMapping("/tinhnguyenvien")
    public String thongKeTNV(
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "status", required = false) String status,
            Model model) {

        int tongTNV = thongKeService.getTongTNV();
        int tongTNVThamGia = thongKeService.getTongTNVThamGia(from, to);
        int tongTNVTheoHoatDong = thongKeService.getTongSoTNVThamGiaTheoHoatDong(from, to, status);

        List<ThongKe> thongKeTNV = thongKeService.getThongKeTNVTheoHoatDong(from, to, null);
        for (ThongKe tk : thongKeTNV) {
            System.out.println("==> " + tk);
        }

        int tongTNVThucTe = thongKeService.getSoLuongTNVThucTeTheoHoatDong(from, to);
        model.addAttribute("tongTNVThucTe", tongTNVThucTe);

        model.addAttribute("tongTNV", tongTNV);
        model.addAttribute("tongTNVThamGia", tongTNVThamGia);
        model.addAttribute("tongTNVTheoHoatDong", tongTNVTheoHoatDong);

        model.addAttribute("thongKeTNV", thongKeTNV);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("status", status);

        return "thongke/tinhnguyenvien";
    }

    // --- Export CSV ---
    @GetMapping("/tinhnguyenvien/export/csv")
    public void exportCSVTinhNguyenVien(
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            HttpServletResponse response) throws IOException {

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=thongke_tinhnguyenvien.csv");

        List<ThongKe> danhSach = thongKeService.getThongKeTNVTheoHoatDong(from, to, null);
        PrintWriter writer = response.getWriter();

        writer.println("Mã hoạt động,Tên hoạt động,Ngày bắt đầu,Ngày kết thúc,Địa điểm,Trạng thái,Số lượng TNV");

        for (ThongKe tk : danhSach) {
            String ngayBD = tk.getNgayBatDau() != null ? tk.getNgayBatDau().toString() : "";
            String ngayKT = tk.getNgayKetThuc() != null ? tk.getNgayKetThuc().toString() : "";

            writer.printf("%s,%s,%s,%s,%s,%s,%d%n",
                    tk.getMaHoatDong(),
                    tk.getTenHoatDong(),
                    ngayBD,
                    ngayKT,
                    tk.getDiaDiem(),
                    tk.getTrangThaiHoatDong(),
                    tk.getSoLuongTNV());
        }

        writer.flush();
        writer.close();
        }

}
