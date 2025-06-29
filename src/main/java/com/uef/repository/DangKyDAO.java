package com.uef.repository;

import com.uef.model.HoatDong;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.uef.model.LichSuDangKy;

@Repository
public class DangKyDAO implements DangKyRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(int maTNV, int maHoatDong) {
        // Cập nhật trạng thái đăng ký
        String sqlUpdate = "UPDATE [DANGKYTHAMGIA] SET trangThai = N'Chờ duyệt' WHERE maThanhVien = ? AND maHoatDong = ?";
        int affected = jdbcTemplate.update(sqlUpdate, maTNV, maHoatDong);
        if (affected == 0) {
            // Nếu chưa có thì insert mới
            String sqlInsert = "INSERT INTO [DANGKYTHAMGIA] (maThanhVien, maHoatDong, trangThai) VALUES (?, ?, N'Chờ duyệt')";
            jdbcTemplate.update(sqlInsert, maTNV, maHoatDong);
        }

        // 3. Lấy lại maDKTG vừa được đảm bảo tồn tại
        String sqlGetMaDKTG = "SELECT maDKTG FROM [DANGKYTHAMGIA] WHERE maThanhVien = ? AND maHoatDong = ?";
        Integer maDKTG = jdbcTemplate.queryForObject(sqlGetMaDKTG, Integer.class, maTNV, maHoatDong);

        // Thêm duyệt đăng ký
        String sqlDuyetDK = "INSERT INTO DUYETDANGKY (maDKTG, maThanhVien, trangThaiDuyet, ghiChu) "
                + "VALUES (?, ?, N'Chờ duyệt', N'Chờ phê duyệt từ hệ thống')";
        jdbcTemplate.update(sqlDuyetDK, maDKTG, maTNV);
        
        // Thêm lịch sử đăng ký
        String sqlHistory = "INSERT INTO LICHSUDANGKY (maThanhVien, maHoatDong, action) VALUES (?, ?, N'Đăng ký')";
        jdbcTemplate.update(sqlHistory, maTNV, maHoatDong);
    }

    @Override
    public void delete(int maTNV, int maHoatDong) {
        // Cập nhật trạng thái hủy
        String sqlUpdate = "UPDATE [DANGKYTHAMGIA] SET trangThai = N'Đã hủy' WHERE maThanhVien = ? AND maHoatDong = ?";
        jdbcTemplate.update(sqlUpdate, maTNV, maHoatDong);
        // Thêm lịch sử đăng ký
        String sqlHistory = "INSERT INTO LICHSUDANGKY (maThanhVien, maHoatDong, action) VALUES (?, ?, N'Hủy đăng ký')";
        jdbcTemplate.update(sqlHistory, maTNV, maHoatDong);
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
        String sql = "SELECT maHoatDong FROM [DANGKYTHAMGIA] WHERE maThanhVien = ? AND trangThai != N'Đã hủy'";
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

    @Override
    public List<HoatDong> findUnregisteredEventsByTNV(int maTNV) {
        // Lấy danh sách các sự kiện mà TNV đã hủy đăng ký (trạng thái = 'Đã hủy')
        String sql = "SELECT hd.* FROM [HOATDONG] hd JOIN [DANGKYTHAMGIA] dk ON hd.maHoatDong = dk.maHoatDong "
                + "WHERE dk.maThanhVien = ? AND dk.trangThai = N'Đã hủy'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(HoatDong.class), maTNV);
    }

    public List<LichSuDangKy> getLichSuDangKy(int maTNV) {
        String sql = "SELECT lsd.*, h.tenHoatDong, h.thoiGianKetThuc, h.diaDiem FROM LICHSUDANGKY lsd "
                + "JOIN HOATDONG h ON lsd.maHoatDong = h.maHoatDong "
                + "WHERE lsd.maThanhVien = ? AND lsd.action = N'Đăng ký' ORDER BY lsd.createAt DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LichSuDangKy lsd = new LichSuDangKy();
            lsd.setId(rs.getInt("id"));
            lsd.setCreateAt(rs.getTimestamp("createAt"));
            lsd.setMaHoatDong(rs.getInt("maHoatDong"));
            lsd.setMaThanhVien(rs.getInt("maThanhVien"));
            lsd.setAction(rs.getString("action"));
            lsd.setTenHoatDong(rs.getString("tenHoatDong"));
            lsd.setThoiGianKetThuc(rs.getTimestamp("thoiGianKetThuc"));
            lsd.setDiaDiem(rs.getString("diaDiem"));
            return lsd;
        }, maTNV);
    }

    public List<LichSuDangKy> getLichSuHuyDangKy(int maTNV) {
        String sql = "SELECT lsd.*, h.tenHoatDong, h.thoiGianKetThuc, h.diaDiem FROM LICHSUDANGKY lsd "
                + "JOIN HOATDONG h ON lsd.maHoatDong = h.maHoatDong "
                + "WHERE lsd.maThanhVien = ? AND lsd.action = N'Hủy đăng ký' ORDER BY lsd.createAt DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LichSuDangKy lsd = new LichSuDangKy();
            lsd.setId(rs.getInt("id"));
            lsd.setCreateAt(rs.getTimestamp("createAt"));
            lsd.setMaHoatDong(rs.getInt("maHoatDong"));
            lsd.setMaThanhVien(rs.getInt("maThanhVien"));
            lsd.setAction(rs.getString("action"));
            lsd.setTenHoatDong(rs.getString("tenHoatDong"));
            lsd.setThoiGianKetThuc(rs.getTimestamp("thoiGianKetThuc"));
            lsd.setDiaDiem(rs.getString("diaDiem"));
            return lsd;
        }, maTNV);
    }
}
