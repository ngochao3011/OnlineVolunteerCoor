package com.uef.model;

import java.util.Date;

public class LichSuThaoTac {

    private int maLichSu;
    private int maThanhVien;
    private String hanhDong;
    private String truocKhiSua;
    private String sauKhiSua;
    private Date thoiGian;

    // Getters & Setters
    public int getMaLichSu() {
        return maLichSu;
    }

    public void setMaLichSu(int maLichSu) {
        this.maLichSu = maLichSu;
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

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }
}
