package com.uef.model;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Volunteer {

    private int maTNV;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String trangThai;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayDangKy;

    public Volunteer() {
    }

    public Volunteer(int maTNV, String hoTen, String sdtDienThoai, String diaChi, String trangThai, Date ngayDangKy) {
        this.maTNV = maTNV;
        this.hoTen = hoTen;
        this.sdt = sdtDienThoai;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.ngayDangKy = ngayDangKy;
    }

    // Getters and Setters
    public int getMaTNV() {
        return maTNV;
    }

    public void setMaTNV(int maTNV) {
        this.maTNV = maTNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdtDienThoai() {
        return sdt;
    }

    public void setSdtDienThoai(String sdtDienThoai) {
        this.sdt = sdtDienThoai;
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
    @Override
    public String toString() {
        return "Volunteer{"
                + "maTNV=" + maTNV
                + ", hoTen='" + hoTen + '\''
                + ", sdtDienThoai='" + sdtDienThoai + '\''
                + ", diaChi='" + diaChi + '\''
                + ", trangThai='" + trangThai + '\''
                + ", ngayDangKy=" + ngayDangKy
                + '}';
    }
}
