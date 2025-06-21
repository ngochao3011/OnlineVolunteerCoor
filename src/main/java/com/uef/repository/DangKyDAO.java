package com.uef.repository;

import com.uef.model.HoatDong;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DangKyDAO implements DangKyRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(int maTNV, int maHoatDong) {
        String sql = "INSERT INTO [DANGKYTHAMGIA] (maThanhVien, maHoatDong, trangThai) VALUES (?, ?, N'Chờ duyệt')";
        jdbcTemplate.update(sql, maTNV, maHoatDong);
    }

    @Override
    public void delete(int maTNV, int maHoatDong) {
        String sql = "DELETE FROM [DANGKYTHAMGIA] WHERE maThanhVien = ? AND maHoatDong = ?";
        jdbcTemplate.update(sql, maTNV, maHoatDong);
    }

    @Override
    public int countActiveRegistrations(int maTNV) {
        // Đếm các hoạt động đang chờ duyệt hoặc đã duyệt
        String sql = "SELECT COUNT(*) FROM [DANGKYTHAMGIA] WHERE maThanhVien = ? AND trangThai IN (N'Chờ duyệt', N'Đã duyệt')";
        return jdbcTemplate.queryForObject(sql, Integer.class, maTNV);
    }

    @Override
    public List<HoatDong> findRegisteredEventsByTNV(int maTNV) {
        // Lấy danh sách các sự kiện mà TNV đã đăng ký (trạng thái chờ hoặc đã duyệt)
        String sql = "SELECT hd.* FROM [HOATDONG] hd JOIN [DANGKYTHAMGIA] dk ON hd.maHoatDong = dk.maHoatDong "
                + "WHERE dk.maThanhVien = ? AND dk.trangThai IN (N'Chờ duyệt', N'Đã duyệt')";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(HoatDong.class), maTNV);
    }

    @Override
    public List<Integer> findRegisteredEventIdsByTNV(int maTNV) {
        String sql = "SELECT maHoatDong FROM [DANGKYTHAMGIA] WHERE maThanhVien = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, maTNV);
    }

    @Override
    public List<HoatDong> findAttendedEventsByTNV(int maTNV) {
        // Lấy tất cả sự kiện đã kết thúc mà TNV được ghi nhận "Có mặt"
      String sql = "SELECT hd.* FROM [HOATDONG] hd "
           + "JOIN [PHIEUDIEMDANH] pd ON hd.maHoatDong = pd.maHoatDong "
           + "JOIN [DIEMDANH] dd ON pd.maDiemDanh = dd.maDiemDanh "
           + "WHERE pd.maThanhVien = ? "
           + "AND dd.trangThai = N'Có mặt' "
           + "AND hd.trangThai = N'Đã kết thúc' "
           + "ORDER BY hd.thoiGianKetThuc DESC";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(HoatDong.class), maTNV);
    }
}
