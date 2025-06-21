package com.uef.repository;

import com.uef.model.DanhGia;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DanhGiaDAO implements DanhGiaRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(DanhGia danhGia) {
        // First insert into DANHGIA table
        String sqlDanhGia = "INSERT INTO [DANHGIA] (maThanhVien, ghiNhanDanhGia) VALUES (?, ?)";
        jdbcTemplate.update(sqlDanhGia, danhGia.getMaTNV(), danhGia.getMoTa());
        
        // Get the generated maDanhGia
        String sqlGetId = "SELECT SCOPE_IDENTITY()";
        Integer maDanhGia = jdbcTemplate.queryForObject(sqlGetId, Integer.class);
        
        // Then insert into PHIEUDANHGIA table
        String sqlPhieu = "INSERT INTO [PHIEUDANHGIA] (maDanhGia, moTa, ngayTao, ghiChu, maThanhVien, maHoatDong) VALUES (?, ?, GETDATE(), ?, ?, ?)";
        jdbcTemplate.update(sqlPhieu, maDanhGia, danhGia.getMoTa(), danhGia.getGhiChu(), danhGia.getMaTNV(), danhGia.getMaHoatDong());
    }

    @Override
    public boolean hasUserReviewedEvent(int maTNV, int maHoatDong) {
        String sql = "SELECT COUNT(*) FROM [PHIEUDANHGIA] WHERE maThanhVien = ? AND maHoatDong = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, maTNV, maHoatDong);
        return count != null && count > 0;
    }

    @Override
    public boolean didUserAttendEvent(int maTNV, int maHoatDong) {
        String sql = "SELECT COUNT(*) FROM [PHIEUDIEMDANH] WHERE maThanhVien = ? AND maHoatDong = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, maTNV, maHoatDong);
        return count != null && count > 0;
    }

    @Override
    public List<Integer> getReviewedEventIdsByUser(int maTNV) {
        String sql = "SELECT DISTINCT maHoatDong FROM [PHIEUDANHGIA] WHERE maThanhVien = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, maTNV);
    }
}
