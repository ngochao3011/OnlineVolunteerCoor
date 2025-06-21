package com.uef.model;

import java.util.Date;

public class DanhGia {

    private int maDanhGia;
    private String maDiemDanhGia; // Có thể dùng làm ID đánh giá duy nhất
    private String moTa;
    private Date ngayTao;
    private String ghiChu;
    private Integer maPV;
    private int maTNV;
    private int maHoatDong;

    public DanhGia() {
    }

    public DanhGia(int maDanhGia, String maDiemDanhGia, String moTa, Date ngayTao, String ghiChu, Integer maPV, int maTNV, int maHoatDong) {
        this.maDanhGia = maDanhGia;
        this.maDiemDanhGia = maDiemDanhGia;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.ghiChu = ghiChu;
        this.maPV = maPV;
        this.maTNV = maTNV;
        this.maHoatDong = maHoatDong;
    }

    public int getMaDanhGia() {
        return maDanhGia;
    }

    public void setMaDanhGia(int maDanhGia) {
        this.maDanhGia = maDanhGia;
    }

    public String getMaDiemDanhGia() {
        return maDiemDanhGia;
    }

    public void setMaDiemDanhGia(String maDiemDanhGia) {
        this.maDiemDanhGia = maDiemDanhGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getMaPV() {
        return maPV;
    }

    public void setMaPV(Integer maPV) {
        this.maPV = maPV;
    }

    public int getMaTNV() {
        return maTNV;
    }

    public void setMaTNV(int maTNV) {
        this.maTNV = maTNV;
    }

    public int getMaHoatDong() {
        return maHoatDong;
    }

    public void setMaHoatDong(int maHoatDong) {
        this.maHoatDong = maHoatDong;
    }
    
}
