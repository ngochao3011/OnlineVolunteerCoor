/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import com.uef.model.TaiKhoan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public class TaiKhoanDAO implements TaiKhoanRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TaiKhoan findByEmail(String email) {
        String sql = "select TK.maTaiKhoan, TK.email, TK.matKhau, TV.ChucVu as quyenHan from TAIKHOAN TK " +
                    "inner join THANHVIEN TV on TK.maTaiKhoan = TV.maThanhVien " +
                    "where email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TaiKhoan.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean save(TaiKhoan taiKhoan) {

        String sql = "INSERT INTO TAIKHOAN (email, matKhau) VALUES (?, ?)";
        return jdbcTemplate.update(sql,
                taiKhoan.getEmail(),
                taiKhoan.getMatKhau()) > 0;

    }

    @Override
    public Integer getID(String email) {
        String sql = "SELECT maTaiKhoan FROM TAIKHOAN WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email);
    }
}
