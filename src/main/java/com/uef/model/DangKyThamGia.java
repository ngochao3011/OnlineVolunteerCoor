package com.uef.model;

import java.util.Date;

public class DangKyThamGia {

    private int maDKTG;
    private int maTNV;
    private Date ngayDangKy;
    private String trangThai;
    private int maSuKien;

    // Getters and Setters
    public int getMaDKTG() {
        return maDKTG;
    }

    public void setMaDKTG(int maDKTG) {
        this.maDKTG = maDKTG;
    }

    public int getMaTNV() {
        return maTNV;
    }

    public void setMaTNV(int maTNV) {
        this.maTNV = maTNV;
    }

    public Date getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(Date ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaSuKien() {
        return maSuKien;
    }

    public void setMaSuKien(int maSuKien) {
        this.maSuKien = maSuKien;
    }
}
