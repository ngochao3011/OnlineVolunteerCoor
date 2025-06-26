/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import com.uef.model.LichSuHoatDong;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LichSuHoatDongRepository {

    private static final Logger logger = LoggerFactory.getLogger(LichSuHoatDongRepository.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<LichSuHoatDong> findAll() {
        String sql = "SELECT * FROM [LICHSUHOATDONG]";
        List<LichSuHoatDong> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
            int maLichSu = rs.getInt("maLichSu");
            int maHoatDong = rs.getInt("maHoatDong");
            int maThanhVien = rs.getInt("maThanhVien");
            String hanhDong = rs.getString("hanhDong");
            String truocKhiSua = rs.getString("truocKhiSua");
            String sauKhiSua = rs.getString("sauKhiSua");
            Timestamp timestamp = rs.getTimestamp("thoiGian");
            LocalDateTime thoiGian = (timestamp != null) ? timestamp.toLocalDateTime() : null;

            if (thoiGian != null) {
                logger.debug("Bản ghi {}: maLichSu={}, thoiGian={}", rowNum + 1, maLichSu, thoiGian.toString());
            } else {
                logger.debug("Bản ghi {}: maLichSu={}, thoiGian=NULL", rowNum + 1, maLichSu);
            }
            return new LichSuHoatDong(maLichSu, maHoatDong, maThanhVien, hanhDong, truocKhiSua, sauKhiSua, thoiGian);
        });
        logger.info("Số lượng bản ghi lấy được: {}", result.size());
        return result;
    }

    public List<LichSuHoatDong> findByMaHoatDong(int maHoatDong) {
        String sql = "SELECT * FROM [LICHSUHOATDONG] WHERE maHoatDong = ?";
        List<LichSuHoatDong> result = jdbcTemplate.query(sql, new Object[]{maHoatDong}, (rs, rowNum) -> {
            int maLichSu = rs.getInt("maLichSu");
            int maHoatDongParam = rs.getInt("maHoatDong");
            int maThanhVien = rs.getInt("maThanhVien");
            String hanhDong = rs.getString("hanhDong");
            String truocKhiSua = rs.getString("truocKhiSua");
            String sauKhiSua = rs.getString("sauKhiSua");
            Timestamp timestamp = rs.getTimestamp("thoiGian");
            LocalDateTime thoiGian = (timestamp != null) ? timestamp.toLocalDateTime() : null;

            if (thoiGian != null) {
                logger.debug("Bản ghi {}: maLichSu={}, thoiGian={}", rowNum + 1, maLichSu, thoiGian.toString());
            } else {
                logger.debug("Bản ghi {}: maLichSu={}, thoiGian=NULL", rowNum + 1, maLichSu);
            }
            return new LichSuHoatDong(maLichSu, maHoatDongParam, maThanhVien, hanhDong, truocKhiSua, sauKhiSua, thoiGian);
        });
        logger.info("Số lượng bản ghi tìm thấy cho maHoatDong {}: {}", maHoatDong, result.size());
        return result;
    }
}
