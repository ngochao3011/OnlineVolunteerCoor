/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

/**
 *
 * @author Asus
 */
import java.time.LocalDate;
import java.util.Map;

public interface ThongKeRepo {

    // ==== THỐNG KÊ HOẠT ĐỘNG ====
    // Line chart theo tháng
    Map<String, Integer> getThongKeTheoThang(LocalDate from, LocalDate to, String status);

    // Pie chart theo trạng thái
    Map<String, Integer> getThongKeTheoTrangThai(LocalDate from, LocalDate to, String status);

    // Summary cards hoạt động
    int getTongSoHoatDong(LocalDate from, LocalDate to, String status);

    int getSoHoatDongThangTruoc(String status);

    int getSoHoanThanh(LocalDate from, LocalDate to);

    int getSoHoanThanhThangTruoc();

    int getSoDangThucHien(LocalDate from, LocalDate to);

    int getSoDangThucHienThangTruoc();

    int getSoDaHuy(LocalDate from, LocalDate to);

    int getSoDaHuyThangTruoc();

    // ==== THỐNG KÊ TÌNH NGUYỆN VIÊN ====
    Map<String, Integer> getThongKeTNVTheoHoatDong(LocalDate from, LocalDate to);

    int getTongTNVThamGia(LocalDate from, LocalDate to);

    int getTongTNVThangTruoc();

    // Line chart TNV theo tháng
    Map<String, Integer> getThongKeTNVTheoThang(LocalDate from, LocalDate to);

    // Tổng TNV theo thời gian
    int getTongTNV(LocalDate from, LocalDate to);

}
