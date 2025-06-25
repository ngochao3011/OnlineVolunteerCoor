/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

/**
 *
 * @author Asus
 */
import com.uef.model.HoatDong;
import com.uef.model.ThongKe;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ThongKeRepo {

    // ---------- Thống kê Hoạt động ----------
    Map<String, Integer> getThongKeTheoThang(LocalDate from, LocalDate to, String status);

    Map<String, Integer> getThongKeTheoTrangThai(LocalDate from, LocalDate to, String status);

    int getTongSoHoatDong(LocalDate from, LocalDate to, String status);

    int getSoHoatDongThangTruoc(String status);

    List<ThongKe> getDanhSachHoatDong(LocalDate from, LocalDate to, String status);

    // ---------- Thống kê theo trạng thái cụ thể ----------
    int getSoKetThuc(LocalDate from, LocalDate to);

    int getSoKetThucThangTruoc();

    int getSoDangHoatDong(LocalDate from, LocalDate to);

    int getSoDangHoatDongThangTruoc();

    int getSoSapDienRa(LocalDate from, LocalDate to);

    int getSoSapDienRaThangTruoc();

    // ---------- Thống kê Tình nguyện viên ----------
    List<ThongKe> getThongKeTNVTheoHoatDong(LocalDate from, LocalDate to, String status);

    int getTongTNV();

    int getTongTNVThamGia(LocalDate from, LocalDate to);
    
    ThongKe getThongKeTongHopTNV(LocalDate from, LocalDate to);

    int getTongSoTNVThamGiaTheoHoatDong(LocalDate from, LocalDate to, String status);
    
    int getSoLuongTNVThucTeTheoHoatDong(LocalDate from, LocalDate to);
    
    

}
