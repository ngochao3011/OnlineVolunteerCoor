/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class LichSuThaoTac {
     private int id;
    private int maTNV;
    private String hanhDong;
    private String truocKhiSua;
    private String sauKhiSua;
    private Date thoiGian;

    public int getId() {
        return id;
    }

    public int getMaTNV() {
        return maTNV;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public String getTruocKhiSua() {
        return truocKhiSua;
    }

    public String getSauKhiSua() {
        return sauKhiSua;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMaTNV(int maTNV) {
        this.maTNV = maTNV;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public void setTruocKhiSua(String truocKhiSua) {
        this.truocKhiSua = truocKhiSua;
    }

    public void setSauKhiSua(String sauKhiSua) {
        this.sauKhiSua = sauKhiSua;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }
    
}
