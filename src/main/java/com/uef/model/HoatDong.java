/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

/**
 *
 * @author Asus
 */
public class HoatDong {
    private int tongSoHoatDong;
    private double tiLeTangTruongTong;

    private int soHoanThanh;
    private double tiLeTangTruongHoanThanh;

    private int soDangThucHien;
    private double tiLeTangTruongDangThucHien;

    private int soDaHuy;
    private double tiLeTangTruongDaHuy;

    public HoatDong() {
    }

    // TỔNG
    public int getTongSoHoatDong() {
        return tongSoHoatDong;
    }

    public void setTongSoHoatDong(int tongSoHoatDong) {
        this.tongSoHoatDong = tongSoHoatDong;
    }

    public double getTiLeTangTruongTong() {
        return tiLeTangTruongTong;
    }

    public void setTiLeTangTruongTong(double tiLeTangTruongTong) {
        this.tiLeTangTruongTong = tiLeTangTruongTong;
    }

    // HOÀN THÀNH
    public int getSoHoanThanh() {
        return soHoanThanh;
    }

    public void setSoHoanThanh(int soHoanThanh) {
        this.soHoanThanh = soHoanThanh;
    }

    public double getTiLeTangTruongHoanThanh() {
        return tiLeTangTruongHoanThanh;
    }

    public void setTiLeTangTruongHoanThanh(double tiLeTangTruongHoanThanh) {
        this.tiLeTangTruongHoanThanh = tiLeTangTruongHoanThanh;
    }

    // ĐANG THỰC HIỆN
    public int getSoDangThucHien() {
        return soDangThucHien;
    }

    public void setSoDangThucHien(int soDangThucHien) {
        this.soDangThucHien = soDangThucHien;
    }

    public double getTiLeTangTruongDangThucHien() {
        return tiLeTangTruongDangThucHien;
    }

    public void setTiLeTangTruongDangThucHien(double tiLeTangTruongDangThucHien) {
        this.tiLeTangTruongDangThucHien = tiLeTangTruongDangThucHien;
    }

    // ĐÃ HỦY
    public int getSoDaHuy() {
        return soDaHuy;
    }

    public void setSoDaHuy(int soDaHuy) {
        this.soDaHuy = soDaHuy;
    }

    public double getTiLeTangTruongDaHuy() {
        return tiLeTangTruongDaHuy;
    }

    public void setTiLeTangTruongDaHuy(double tiLeTangTruongDaHuy) {
        this.tiLeTangTruongDaHuy = tiLeTangTruongDaHuy;
    }
}