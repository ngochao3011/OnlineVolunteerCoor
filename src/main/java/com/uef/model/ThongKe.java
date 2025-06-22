/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

/**
 *
 * @author Asus
 */
public class ThongKe {

    // Thống kê hoạt động 
    private int tongSoHoatDong;
    private int soHoanThanh;
    private int soDangThucHien;
    private int soDaHuy;
    private double tiLeTangTruongTong;
    private double tiLeTangTruongHoanThanh;
    private double tiLeTangTruongDangThucHien;
    private double tiLeTangTruongDaHuy;

    // Thống kê tình nguyện viên 
    private int tongTNV;
    private int tongSo;
    private double tiLeTangTruong;

    public int getTongTNV() {
        return tongTNV;
    }

    public void setTongTNV(int tongTNV) {
        this.tongTNV = tongTNV;
    }

    private String thang;
    private int soLuongTNV;

    // Getters & Setters
    //  phần hoạt động 
    public int getTongSoHoatDong() {
        return tongSoHoatDong;
    }

    public void setTongSoHoatDong(int tongSoHoatDong) {
        this.tongSoHoatDong = tongSoHoatDong;
    }

    public int getSoHoanThanh() {
        return soHoanThanh;
    }

    public void setSoHoanThanh(int soHoanThanh) {
        this.soHoanThanh = soHoanThanh;
    }

    public int getSoDangThucHien() {
        return soDangThucHien;
    }

    public void setSoDangThucHien(int soDangThucHien) {
        this.soDangThucHien = soDangThucHien;
    }

    public int getSoDaHuy() {
        return soDaHuy;
    }

    public void setSoDaHuy(int soDaHuy) {
        this.soDaHuy = soDaHuy;
    }

    public double getTiLeTangTruongTong() {
        return tiLeTangTruongTong;
    }

    public void setTiLeTangTruongTong(double tiLeTangTruongTong) {
        this.tiLeTangTruongTong = tiLeTangTruongTong;
    }

    public double getTiLeTangTruongHoanThanh() {
        return tiLeTangTruongHoanThanh;
    }

    public void setTiLeTangTruongHoanThanh(double tiLeTangTruongHoanThanh) {
        this.tiLeTangTruongHoanThanh = tiLeTangTruongHoanThanh;
    }

    public double getTiLeTangTruongDangThucHien() {
        return tiLeTangTruongDangThucHien;
    }

    public void setTiLeTangTruongDangThucHien(double tiLeTangTruongDangThucHien) {
        this.tiLeTangTruongDangThucHien = tiLeTangTruongDangThucHien;
    }

    public double getTiLeTangTruongDaHuy() {
        return tiLeTangTruongDaHuy;
    }

    public void setTiLeTangTruongDaHuy(double tiLeTangTruongDaHuy) {
        this.tiLeTangTruongDaHuy = tiLeTangTruongDaHuy;
    }

    //  phần TNV
    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public int getSoLuongTNV() {
        return soLuongTNV;
    }

    public void setSoLuongTNV(int soLuongTNV) {
        this.soLuongTNV = soLuongTNV;
    }

    public int getTongSo() {
        return tongSo;
    }

    public double getTiLeTangTruong() {
        return tiLeTangTruong;
    }

    public void setTongSo(int tongSo) {
        this.tongSo = tongSo;
    }

    public void setTiLeTangTruong(double tiLeTangTruong) {
        this.tiLeTangTruong = tiLeTangTruong;
    }
    
}
