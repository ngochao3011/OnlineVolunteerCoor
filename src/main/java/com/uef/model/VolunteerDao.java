package com.uef.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VolunteerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Lấy tất cả tình nguyện viên
    public List<Volunteer> getAll() {
        String sql = "SELECT * FROM [Tình Nguyện Viên]";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Volunteer.class));
    }

    // Lấy theo mã tình nguyện viên
    public Volunteer getById(int id) {
        String sql = "SELECT * FROM [Tình Nguyện Viên] WHERE maTNV = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Volunteer.class), id);
    }

    // Xóa
    public boolean delete(int id) {
        String sql = "DELETE FROM [Tình Nguyện Viên] WHERE maTNV = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    // Tìm kiếm theo tên
    public List<Volunteer> searchByName(String keyword) {
        String sql = "SELECT * FROM [Tình Nguyện Viên] WHERE hoTen LIKE ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Volunteer.class), "%" + keyword + "%");
    }
}
