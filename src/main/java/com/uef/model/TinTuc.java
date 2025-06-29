/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class TinTuc {

    public int id;
    public String tieuDe;
    public String noiDung;
    public String hinhAnh;
    public String ngayDang;
    public String tacGia;
    public int soBinhLuan;
    public String danhMuc;
    public List<String> hashtags;

    // Constructor, getters/setters
    public TinTuc(int id, String tieuDe, String noiDung, String hinhAnh, String ngayDang, String tacGia, int soBinhLuan, String danhMuc) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.hinhAnh = hinhAnh;
        this.ngayDang = ngayDang;
        this.tacGia = tacGia;
        this.soBinhLuan = soBinhLuan;
        this.danhMuc = danhMuc;
        this.hashtags = hashtags;
    }

    public TinTuc(int id, String tieuDe, String noiDung, String hinhAnh, String ngayDang, String tacGia, int soBinhLuan, String danhMuc, List<String> hashtags) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.hinhAnh = hinhAnh;
        this.ngayDang = ngayDang;
        this.tacGia = tacGia;
        this.soBinhLuan = soBinhLuan;
        this.danhMuc = danhMuc;
        this.hashtags = hashtags;
    }

    public TinTuc() {
    }

    public int getId() {
        return id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public String getTacGia() {
        return tacGia;
    }

    public int getSoBinhLuan() {
        return soBinhLuan;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public void setSoBinhLuan(int soBinhLuan) {
        this.soBinhLuan = soBinhLuan;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

}
