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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Map;

@Service
public class ThongKeService {

    @Autowired
    private ThongKeRepo thongKeRepo;

    // ================== THỐNG KÊ HOẠT ĐỘNG ===================
    public Map<String, Integer> laySoLieuThongKeTheoThang(LocalDate from, LocalDate to, String status) {
        return thongKeRepo.getThongKeTheoThang(from, to, status);
    }

    public Map<String, Integer> laySoLieuThongKeTheoTrangThai(LocalDate from, LocalDate to, String status) {
        return thongKeRepo.getThongKeTheoTrangThai(from, to, status);
    }

    public ThongKe layThongKeTongQuan(LocalDate from, LocalDate to, String status) {
        ThongKe thongKe = new ThongKe();

        int tong = thongKeRepo.getTongSoHoatDong(from, to, status);
        int tongTruoc = thongKeRepo.getSoHoatDongThangTruoc(status);
        thongKe.setTongSoHoatDong(tong);
        thongKe.setTiLeTangTruongTong(tinhTiLe(tong, tongTruoc));

        int hoanThanh = thongKeRepo.getSoHoanThanh(from, to);
        int hoanThanhTruoc = thongKeRepo.getSoHoanThanhThangTruoc();
        thongKe.setSoHoanThanh(hoanThanh);
        thongKe.setTiLeTangTruongHoanThanh(tinhTiLe(hoanThanh, hoanThanhTruoc));

        int dangThucHien = thongKeRepo.getSoDangThucHien(from, to);
        int dangThucHienTruoc = thongKeRepo.getSoDangThucHienThangTruoc();
        thongKe.setSoDangThucHien(dangThucHien);
        thongKe.setTiLeTangTruongDangThucHien(tinhTiLe(dangThucHien, dangThucHienTruoc));

        int daHuy = thongKeRepo.getSoDaHuy(from, to);
        int daHuyTruoc = thongKeRepo.getSoDaHuyThangTruoc();
        thongKe.setSoDaHuy(daHuy);
        thongKe.setTiLeTangTruongDaHuy(tinhTiLe(daHuy, daHuyTruoc));

        return thongKe;
    }

    // ================== THỐNG KÊ TÌNH NGUYỆN VIÊN ===================
    public Map<String, Integer> layThongKeTNVTheoHoatDong(LocalDate from, LocalDate to) {
        return thongKeRepo.getThongKeTNVTheoHoatDong(from, to);
    }

    public int layTongTNVThamGia(LocalDate from, LocalDate to) {
        return thongKeRepo.getTongTNVThamGia(from, to);
    }

    public int layTongTNVThangTruoc() {
        return thongKeRepo.getTongTNVThangTruoc();
    }

    // ================== EXPORT EXCEL ===================
    public void exportThongKeHoatDongToExcel(OutputStream out, LocalDate from, LocalDate to, String status) {
        Map<String, Integer> data = thongKeRepo.getThongKeTheoThang(from, to, status);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Thống kê hoạt động");

            // Tạo tiêu đề
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Tháng");
            header.createCell(1).setCellValue("Số lượng hoạt động");

            // Đổ dữ liệu
            int rowIdx = 1;
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================== HỖ TRỢ ===================
    private double tinhTiLe(int hienTai, int truocDo) {
        if (truocDo == 0) {
            return hienTai > 0 ? 100.0 : 0.0;
        }
        return ((double) (hienTai - truocDo) / truocDo) * 100;
    }

}
