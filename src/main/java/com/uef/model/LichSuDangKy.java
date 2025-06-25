package com.uef.model;

import java.util.Date;

public class LichSuDangKy {
    private int id;
    private Date createAt;
    private int maHoatDong;
    private int maThanhVien;
    private String action;
    private String tenHoatDong;
    private java.util.Date thoiGianKetThuc;
    private String diaDiem;

    public LichSuDangKy() {}

    public LichSuDangKy(int id, Date createAt, int maHoatDong, int maThanhVien) {
        this.id = id;
        this.createAt = createAt;
        this.maHoatDong = maHoatDong;
        this.maThanhVien = maThanhVien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTenHoatDong() {
        return tenHoatDong;
    }

    public void setTenHoatDong(String tenHoatDong) {
        this.tenHoatDong = tenHoatDong;
    }

    public java.util.Date getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(java.util.Date thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }
} 