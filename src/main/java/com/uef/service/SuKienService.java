/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

import com.uef.model.SuKien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;

@Service
public class SuKienService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Thêm sự kiện mới
    public void themSuKien(SuKien suKien) {
        String sql = "INSERT INTO [Sự Kiện] (tenSuKien, moTa, thoiGianBatDau, thoiGianKetThuc, diaDiem, trangThai) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, suKien.getTenSuKien(), suKien.getMoTa(), suKien.getThoiGianBatDau(),
                suKien.getThoiGianKetThuc(), suKien.getDiaDiem(), suKien.getTrangThai());
    }

    // Cập nhật sự kiện
    public void capNhatSuKien(SuKien suKien) {
        String sql = "UPDATE [Sự Kiện] SET tenSuKien = ?, moTa = ?, thoiGianBatDau = ?, thoiGianKetThuc = ?, diaDiem = ?, trangThai = ? WHERE maSuKien = ?";
        jdbcTemplate.update(sql, suKien.getTenSuKien(), suKien.getMoTa(), suKien.getThoiGianBatDau(),
                suKien.getThoiGianKetThuc(), suKien.getDiaDiem(), suKien.getTrangThai(), suKien.getMaSuKien());
    }

    // Xóa sự kiện
    public void xoaSuKien(int maSuKien) {
        String sql = "DELETE FROM [Sự Kiện] WHERE maSuKien = ?";
        jdbcTemplate.update(sql, maSuKien);
    }

    // Lấy danh sách tất cả sự kiện
    public List<SuKien> layDanhSachSuKien() {
        System.out.println("Current java.library.path: " + System.getProperty("java.library.path"));
        String sql = "SELECT * FROM [Sự Kiện]";
        try {
        return jdbcTemplate.query(sql, (rs, rowNum) -> new SuKien(
                rs.getInt("maSuKien"),
                rs.getString("tenSuKien"),
                rs.getString("moTa"),
                rs.getObject("thoiGianBatDau", LocalDateTime.class),
                rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                rs.getString("diaDiem"),
                rs.getString("trangThai")
        ));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("JDBC error: " + e.getMessage());
        throw e;
    }
    }

    // Lấy sự kiện theo mã
    public SuKien laySuKienTheoMa(int maSuKien) {
        String sql = "SELECT * FROM [Sự Kiện] WHERE maSuKien = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{maSuKien}, (rs, rowNum) -> new SuKien(
                rs.getInt("maSuKien"),
                rs.getString("tenSuKien"),
                rs.getString("moTa"),
                rs.getObject("thoiGianBatDau", LocalDateTime.class),
                rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                rs.getString("diaDiem"),
                rs.getString("trangThai")
        ));
    }
}
