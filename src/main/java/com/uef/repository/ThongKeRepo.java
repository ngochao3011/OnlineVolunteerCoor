/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;


/**
 *
 * @author Asus
 */
import java.util.Map;

public interface ThongKeRepo {

    Map<String, Integer> getThongKeTheoThang();
    Map<String, Integer> getThongKeTheoTrangThai();

    int getTongSoHoatDong();
    int getSoHoatDongThangTruoc();

    int getSoHoanThanh();
    int getSoHoanThanhThangTruoc();

    int getSoDangThucHien();
    int getSoDangThucHienThangTruoc();

    int getSoDaHuy();
    int getSoDaHuyThangTruoc();

    // Dành cho lọc theo khoảng thời gian
    int getSoHoatDongBetween(String from, String to);
    int getSoTrangThaiBetween(String trangThai, String from, String to);
}
