/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

import java.sql.Date;

/**
 *
 * @author Asus
 */
/**
 *
 * @author Asus
 */
public class ThongKe {

    private String maHoatDong;
    private String tenHoatDong;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String diaDiem;

    // ========== THỐNG KÊ HOẠT ĐỘNG ==========
    private String trangThaiHoatDong;
    private int tongSoHoatDong;
    private int soHoatDongHoanThanh;
    private int soHoatDongDangThucHien;
    private int soHoatDongDaHuy;

    // ========== THỐNG KÊ THEO THỜI GIAN ==========
    private Date tuNgay;
    private Date denNgay;

    // ========== CONSTRUCTORS ==========
    public ThongKe() {
    }

    // ========== GETTERS & SETTERS ==========
    // Thông tin chung
    public String getMaHoatDong() {
        return maHoatDong;
    }

    public void setMaHoatDong(String maHoatDong) {
        this.maHoatDong = maHoatDong;
    }

    public String getTenHoatDong() {
        return tenHoatDong;
    }

    public void setTenHoatDong(String tenHoatDong) {
        this.tenHoatDong = tenHoatDong;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    // Thống kê hoạt động
    public String getTrangThaiHoatDong() {
        return trangThaiHoatDong;
    }

    public void setTrangThaiHoatDong(String trangThaiHoatDong) {
        this.trangThaiHoatDong = trangThaiHoatDong;
    }

    public int getTongSoHoatDong() {
        return tongSoHoatDong;
    }

    public void setTongSoHoatDong(int tongSoHoatDong) {
        this.tongSoHoatDong = tongSoHoatDong;
    }

    public int getSoHoatDongHoanThanh() {
        return soHoatDongHoanThanh;
    }

    public void setSoHoatDongHoanThanh(int soHoatDongHoanThanh) {
        this.soHoatDongHoanThanh = soHoatDongHoanThanh;
    }

    public int getSoHoatDongDangThucHien() {
        return soHoatDongDangThucHien;
    }

    public void setSoHoatDongDangThucHien(int soHoatDongDangThucHien) {
        this.soHoatDongDangThucHien = soHoatDongDangThucHien;
    }

    public int getSoHoatDongDaHuy() {
        return soHoatDongDaHuy;
    }

    public void setSoHoatDongDaHuy(int soHoatDongDaHuy) {
        this.soHoatDongDaHuy = soHoatDongDaHuy;
    }

    // Thống kê theo thời gian
    public Date getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(Date tuNgay) {
        this.tuNgay = tuNgay;
    }

    public Date getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(Date denNgay) {
        this.denNgay = denNgay;
    }

    private double tiLeTangTruongTong;

    public double getTiLeTangTruongTong() {
        return tiLeTangTruongTong;
    }

    public void setTiLeTangTruongTong(double tiLeTangTruongTong) {
        this.tiLeTangTruongTong = tiLeTangTruongTong;
    }

    private int soHoanThanh;

    public int getSoHoanThanh() {
        return soHoanThanh;
    }

    public void setSoHoanThanh(int soHoanThanh) {
        this.soHoanThanh = soHoanThanh;
    }

    private double tiLeTangTruongHoanThanh;

    public double getTiLeTangTruongHoanThanh() {
        return tiLeTangTruongHoanThanh;
    }

    public void setTiLeTangTruongHoanThanh(double tiLeTangTruongHoanThanh) {
        this.tiLeTangTruongHoanThanh = tiLeTangTruongHoanThanh;
    }

    private int soDangThucHien;

    public void setSoDangThucHien(int soDangThucHien) {
        this.soDangThucHien = soDangThucHien;
    }

    public int getSoDangThucHien() {
        return soDangThucHien;
    }

    private double tiLeTangTruongDangThucHien;

    public void setTiLeTangTruongDangThucHien(double tiLeTangTruongDangThucHien) {
        this.tiLeTangTruongDangThucHien = tiLeTangTruongDangThucHien;
    }

    public double getTiLeTangTruongDangThucHien() {
        return tiLeTangTruongDangThucHien;
    }

    private int soDaHuy;

    public void setSoDaHuy(int soDaHuy) {
        this.soDaHuy = soDaHuy;
    }

    public int getSoDaHuy() {
        return soDaHuy;
    }

    private double tiLeTangTruongDaHuy;

    public void setTiLeTangTruongDaHuy(double tiLeTangTruongDaHuy) {
        this.tiLeTangTruongDaHuy = tiLeTangTruongDaHuy;
    }

    public double getTiLeTangTruongDaHuy() {
        return tiLeTangTruongDaHuy;
    }

//TNV
    private int tongTNV;
    private int tongTNVThamGia;
    private int soLuongTNV;
    private int maThanhVien;
private String hoTen;
private String email;
private String sdt;
   

    public int getSoLuongTNV() {
        return soLuongTNV;
    }

    public void setSoLuongTNV(int soLuongTNV) {
        this.soLuongTNV = soLuongTNV;
    }

    public int getTongTNV() {
        return tongTNV;
    }

    public void setTongTNV(int tongTNV) {
        this.tongTNV = tongTNV;
    }

    public int getTongTNVThamGia() {
        return tongTNVThamGia;
    }

    public void setTongTNVThamGia(int tongTNVThamGia) {
        this.tongTNVThamGia = tongTNVThamGia;
    }

    @Override
    public String toString() {
        return "ThongKe {"
                + "maHoatDong='" + maHoatDong + '\''
                + ", tenHoatDong='" + tenHoatDong + '\''
                + ", ngayBatDau=" + ngayBatDau
                + ", ngayKetThuc=" + ngayKetThuc
                + ", diaDiem='" + diaDiem + '\''
                + ", trangThaiHoatDong='" + trangThaiHoatDong + '\''
                + ", soLuongTNV=" + soLuongTNV
                + ", tongTNV=" + tongTNV
                + ", tongTNVThamGia=" + tongTNVThamGia
                + '}';
    }

}