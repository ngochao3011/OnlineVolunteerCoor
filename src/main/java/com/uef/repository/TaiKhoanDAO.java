/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import com.uef.model.TaiKhoan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        String sql = "SELECT * FROM [Tài Khoản] WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TaiKhoan.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int save(TaiKhoan taiKhoan) {
        try {
            String sql = "INSERT INTO [Tài Khoản] (email, matKhau, hoTen, sdtDienThoai, quyenHan) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    taiKhoan.getEmail(),
                    taiKhoan.getMatKhau(),
                    taiKhoan.getHoTen(),
                    taiKhoan.getSdt(),
                    taiKhoan.getQuyenHan());

            // Lấy ID vừa tạo
            return jdbcTemplate.queryForObject("SELECT IDENT_CURRENT('[Tài Khoản]')", Integer.class);

        } catch (DuplicateKeyException e) {
            // Ném lỗi có ý nghĩa
            throw new IllegalArgumentException("Email đã tồn tại. Vui lòng sử dụng email khác.");
        }
    }
}
