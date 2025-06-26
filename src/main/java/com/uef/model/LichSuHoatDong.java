/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

import java.time.LocalDateTime;

public class LichSuHoatDong {

    private int maLichSu;
    private int maHoatDong;
    private int maThanhVien;
    private String hanhDong; // 'Sửa' hoặc 'Xóa'
    private String truocKhiSua; // JSON string trước khi thay đổi
    private String sauKhiSua; // JSON string sau khi thay đổi (chỉ với 'Sửa')
    private LocalDateTime thoiGian;

    // Constructors
    public LichSuHoatDong() {
    }

    public LichSuHoatDong(int maLichSu, int maHoatDong, int maThanhVien, String hanhDong, String truocKhiSua, String sauKhiSua, LocalDateTime thoiGian) {
        this.maLichSu = maLichSu;
        this.maHoatDong = maHoatDong;
        this.maThanhVien = maThanhVien;
        this.hanhDong = hanhDong;
        this.truocKhiSua = truocKhiSua;
        this.sauKhiSua = sauKhiSua;
        this.thoiGian = thoiGian;
    }

    // Getters and Setters
    public int getMaLichSu() {
        return maLichSu;
    }

    public void setMaLichSu(int maLichSu) {
        this.maLichSu = maLichSu;
    }

    public int getMaHoatDong() {
        return maHoatDong;
    }

    public void setMaHoatDong(int maHoatDong) {
        this.maHoatDong = maHoatDong;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public String getTruocKhiSua() {
        return truocKhiSua;
    }

    public void setTruocKhiSua(String truocKhiSua) {
        this.truocKhiSua = truocKhiSua;
    }

    public String getSauKhiSua() {
        return sauKhiSua;
    }

    public void setSauKhiSua(String sauKhiSua) {
        this.sauKhiSua = sauKhiSua;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }
}
