/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import com.uef.model.TinTuc;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Asus
 */
@Repository
public class TinTucDAO implements TinTucRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TinTuc> getTinNgauNhien() {
        String sql = "SELECT TOP 3 * FROM TIN_TUC ORDER BY NEWID()";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TinTuc.class));
    }

    @Override
    public List<TinTuc> getTinGanDay() {
        String sql = "SELECT TOP 5 * FROM TIN_TUC ORDER BY ngayDang DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TinTuc.class));
    }

    @Override
    public List<String> getDanhMucs() {
        String sql = "SELECT DISTINCT danhMuc FROM TIN_TUC";
        return jdbcTemplate.queryForList(sql, String.class);
    }
    
    @Override
    public List<String> getTopHashtags() {
    String sql = "SELECT TOP 10 h.tenTag " +
                 "FROM TIN_TUC_HASHTAG th " +
                 "JOIN HASHTAG h ON th.hashtagId = h.id " +
                 "GROUP BY h.tenTag ORDER BY COUNT(*) DESC";
    return jdbcTemplate.queryForList(sql, String.class);
}
}