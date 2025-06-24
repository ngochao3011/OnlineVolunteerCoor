/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

import java.time.LocalDateTime;

public class DuyetDangKy {

    private int maDDK;
    private int maDKTG;
    private int maThanhVien;
    private LocalDateTime ngayDuyet;
    private String trangThaiDuyet;
    private String ghiChu;
    private String hoTen;
    private String email;
    private HoatDong hoatDong;

    // Constructors
    public DuyetDangKy() {
    }

    public DuyetDangKy(int maDDK, int maDKTG, int maThanhVien, LocalDateTime ngayDuyet, String trangThaiDuyet, String ghiChu,
                       String hoTen, String email, Integer maHoatDong, String tenHoatDong, String moTa, LocalDateTime thoiGianBatDau,
                       LocalDateTime thoiGianKetThuc, String diaDiem, String trangThaiHoatDong) {
        this.maDDK = maDDK;
        this.maDKTG = maDKTG;
        this.maThanhVien = maThanhVien;
        this.ngayDuyet = ngayDuyet;
        this.trangThaiDuyet = trangThaiDuyet;
        this.ghiChu = ghiChu;
        this.hoTen = hoTen;
        this.email = email;
        this.hoatDong = (maHoatDong != null && tenHoatDong != null) ? new HoatDong(
                maHoatDong, tenHoatDong, moTa, thoiGianBatDau, thoiGianKetThuc, diaDiem, trangThaiHoatDong) : null;
    }

    // Getters and Setters
    public int getMaDDK() {
        return maDDK;
    }

    public void setMaDDK(int maDDK) {
        this.maDDK = maDDK;
    }

    public int getMaDKTG() {
        return maDKTG;
    }

    public void setMaDKTG(int maDKTG) {
        this.maDKTG = maDKTG;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public LocalDateTime getNgayDuyet() {
        return ngayDuyet;
    }

    public void setNgayDuyet(LocalDateTime ngayDuyet) {
        this.ngayDuyet = ngayDuyet;
    }

    public String getTrangThaiDuyet() {
        return trangThaiDuyet;
    }

    public void setTrangThaiDuyet(String trangThaiDuyet) {
        this.trangThaiDuyet = trangThaiDuyet;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HoatDong getHoatDong() {
        return hoatDong;
    }

    public void setHoatDong(HoatDong hoatDong) {
        this.hoatDong = hoatDong;
    }

    
    
}
