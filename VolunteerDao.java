package com.uef.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.text.SimpleDateFormat;

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

    // Thêm mới
    public boolean save(Volunteer volunteer) {
        String sql = "INSERT INTO [Tình Nguyện Viên] (maTNV, hoTen, sdtDienThoai, diaChi, trangThai, ngayDangKy) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ngayDangKy = volunteer.getNgayDangKy() != null ? sdf.format(volunteer.getNgayDangKy()) : null;
        return jdbcTemplate.update(sql,
                volunteer.getMaTNV(),
                volunteer.getHoTen(),
                volunteer.getSdtDienThoai(),
                volunteer.getDiaChi(),
                volunteer.getTrangThai(),
                ngayDangKy) > 0;
    }

    // Cập nhật
    public boolean update(Volunteer v) {
        String sql = "UPDATE [Tình Nguyện Viên] SET hoTen=?, sdtDienThoai=?, diaChi=?, trangThai=?, ngayDangKy=? "
                + "WHERE maTNV=?";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ngayDangKy = v.getNgayDangKy() != null ? sdf.format(v.getNgayDangKy()) : null;
        return jdbcTemplate.update(sql,
                v.getHoTen(),
                v.getSdtDienThoai(),
                v.getDiaChi(),
                v.getTrangThai(),
                ngayDangKy,
                v.getMaTNV()) > 0;
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
