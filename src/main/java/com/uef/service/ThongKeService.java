/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Asus
 */
package com.uef.service;

import com.uef.model.ThongKe;
import com.uef.repository.ThongKeRepo;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ThongKeService {

    @Autowired
    private ThongKeRepo thongKeRepo;

    public Map<String, Integer> laySoLieuThongKeTheoThang(LocalDate from, LocalDate to, String status) {
        return thongKeRepo.getThongKeTheoThang(from, to, status);
    }

    public Map<String, Integer> laySoLieuThongKeTheoTrangThai(LocalDate from, LocalDate to, String status) {
        return thongKeRepo.getThongKeTheoTrangThai(from, to, status);
    }

    public int getSoDaKetThuc(LocalDate from, LocalDate to) {
        return thongKeRepo.getSoKetThuc(from, to);
    }

    public int getSoDangHoatDong(LocalDate from, LocalDate to) {
        return thongKeRepo.getSoDangHoatDong(from, to);
    }

    public int getSoSapDienRa(LocalDate from, LocalDate to) {
        return thongKeRepo.getSoSapDienRa(from, to);
    }

    public ThongKe layThongKeTongQuan(LocalDate from, LocalDate to, String status) {
        ThongKe thongKe = new ThongKe();

        Map<String, Integer> theoTrangThai = thongKeRepo.getThongKeTheoTrangThai(from, to, status);

        // Tổng số = tổng 3 trạng thái
        int tong = theoTrangThai.values().stream().mapToInt(Integer::intValue).sum();
        thongKe.setTongSoHoatDong(tong);

        // Gán theo đúng trạng thái thống kê
        thongKe.setSoHoanThanh(theoTrangThai.getOrDefault("Đã kết thúc", 0));
        thongKe.setSoDangThucHien(theoTrangThai.getOrDefault("Đang hoạt động", 0));
        thongKe.setSoDaHuy(theoTrangThai.getOrDefault("Sắp diễn ra", 0));

        // Không tính tăng trưởng ở đây
        thongKe.setTiLeTangTruongTong(0);
        thongKe.setTiLeTangTruongHoanThanh(0);
        thongKe.setTiLeTangTruongDangThucHien(0);
        thongKe.setTiLeTangTruongDaHuy(0);

        return thongKe;
    }

    // ================== EXPORT EXCEL ===================
    public List<ThongKe> getDanhSachHoatDong(LocalDate from, LocalDate to, String status) {
        return thongKeRepo.getDanhSachHoatDong(from, to, status);
    }

    public void exportExcelThongKe(LocalDate from, LocalDate to, String status, OutputStream out) throws IOException {
        List<ThongKe> danhSach = thongKeRepo.getDanhSachHoatDong(from, to, status);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("ThongKe");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Mã");
            header.createCell(1).setCellValue("Tên");
            header.createCell(2).setCellValue("Ngày bắt đầu");
            header.createCell(3).setCellValue("Ngày kết thúc");
            header.createCell(4).setCellValue("Địa điểm");
            header.createCell(5).setCellValue("Trạng thái");

            int rowIndex = 1;
            for (ThongKe tk : danhSach) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(tk.getMaHoatDong());
                row.createCell(1).setCellValue(tk.getTenHoatDong());
                row.createCell(2).setCellValue(tk.getNgayBatDau().toString());
                row.createCell(3).setCellValue(tk.getNgayKetThuc().toString());
                row.createCell(4).setCellValue(tk.getDiaDiem());
                row.createCell(5).setCellValue(tk.getTrangThaiHoatDong());
            }

            workbook.write(out);
        }
    }

    private double tinhTiLe(int hienTai, int truocDo) {
        if (truocDo == 0) {
            return hienTai > 0 ? 100.0 : 0.0;
        }
        return ((double) (hienTai - truocDo) / truocDo) * 100;
    }

    //tnv
    // Lấy danh sách hoạt động và số TNV đã tham gia (theo thời gian + trạng thái)
    public List<ThongKe> getThongKeTNVTheoHoatDong(LocalDate from, LocalDate to, String status) {
        return thongKeRepo.getThongKeTNVTheoHoatDong(from, to, status);
    }

    public int getSoLuongTNVThucTeTheoHoatDong(LocalDate from, LocalDate to) {
        return thongKeRepo.getSoLuongTNVThucTeTheoHoatDong(from, to);
    }


    public int getTongTNV() {
        return thongKeRepo.getTongTNV();
    }

    public int getTongTNVThamGia(LocalDate from, LocalDate to) {
        return thongKeRepo.getTongTNVThamGia(from, to);
    }

    public void exportExcelTNVTheoHoatDong(LocalDate from, LocalDate to, String status, OutputStream out) throws IOException {
        List<ThongKe> danhSach = thongKeRepo.getThongKeTNVTheoHoatDong(from, to, status);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("TNV_Theo_HoatDong");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Mã");
            header.createCell(1).setCellValue("Tên hoạt động");
            header.createCell(2).setCellValue("Ngày bắt đầu");
            header.createCell(3).setCellValue("Ngày kết thúc");
            header.createCell(4).setCellValue("Địa điểm");
            header.createCell(5).setCellValue("Trạng thái");
            header.createCell(6).setCellValue("Số TNV tham gia");

            int rowIndex = 1;
            for (ThongKe tk : danhSach) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(tk.getMaHoatDong());
                row.createCell(1).setCellValue(tk.getTenHoatDong());
                row.createCell(2).setCellValue(tk.getNgayBatDau().toString());
                row.createCell(3).setCellValue(tk.getNgayKetThuc().toString());
                row.createCell(4).setCellValue(tk.getDiaDiem());
                row.createCell(5).setCellValue(tk.getTrangThaiHoatDong());
                row.createCell(6).setCellValue(tk.getSoLuongTNV());
            }

            workbook.write(out);
        }
    }

    public ThongKe layThongKeTongQuanTNV(LocalDate from, LocalDate to) {
        ThongKe thongKe = new ThongKe();
        thongKe.setTongTNV(thongKeRepo.getTongTNV());
        thongKe.setTongTNVThamGia(thongKeRepo.getTongTNVThamGia(from, to));
        return thongKe;
    }

    public int getTongSoTNVThamGiaTheoHoatDong(LocalDate from, LocalDate to, String status) {
        return thongKeRepo.getTongSoTNVThamGiaTheoHoatDong(from, to, status);
    }

}
