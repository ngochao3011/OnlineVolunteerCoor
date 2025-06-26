package com.uef.model;

import java.sql.Date;

public class Volunteer {

    private int maThanhVien;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String trangThai;
    private Date ngayDangKy;
    private String urlAvatar;
    private String chucVu; // tương đương quyền hạn

    // Getter và Setter
    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(Date ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    @Override
    public String toString() {
        return "Mã thành viên: " + maThanhVien
                + ", Họ tên: " + hoTen
                + ", Số điện thoại: " + sdt
                + ", Địa chỉ: " + diaChi
                + ", Trạng thái: " + trangThai
                + ", Ngày đăng ký: " + ngayDangKy;
    }
}
