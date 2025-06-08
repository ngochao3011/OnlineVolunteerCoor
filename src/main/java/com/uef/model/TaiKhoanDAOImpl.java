/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

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
public class TaiKhoanDAOImpl implements TaiKhoanDAO {

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
    public boolean save(TaiKhoan tk) {
        String sql = "INSERT INTO [Tài Khoản] (email, matKhau, hoTen, sdtDienThoai, quyenHan) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, tk.getEmail(), tk.getMatKhau(), tk.getHoTen(), tk.getSdt(), tk.getQuyenHan()) > 0;
    }
}
