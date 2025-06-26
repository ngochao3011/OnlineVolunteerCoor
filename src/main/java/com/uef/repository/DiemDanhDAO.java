/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public class DiemDanhDAO implements DiemDanhRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean checkDiemDanh(int maThanhVien, int maHoatDong) {
        String sql = "select * from PHIEUDIEMDANH where trangThai = N'Có mặt' and maHoatDong = ? and maThanhVien = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, maHoatDong, maThanhVien) > 0;
    }

    @Override
    public boolean checkin(int maThanhVien, int maHoatDong) {
        String sql = "update PHIEUDIEMDANH set trangThai = N'Có mặt' where maHoatDong = ? and maThanhVien = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, maHoatDong, maThanhVien) > 0;
    }
    
    
}
