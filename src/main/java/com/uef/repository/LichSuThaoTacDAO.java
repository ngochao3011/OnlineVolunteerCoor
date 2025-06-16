package com.uef.repository;

import com.uef.model.LichSuThaoTac;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LichSuThaoTacDAO implements LichSuRepo {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public LichSuThaoTacDAO(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean save(LichSuThaoTac log) {
        String sql = "INSERT INTO LICHSUTHAOTAC (maThanhVien, hanhDong, truocKhiSua, sauKhiSua, thoiGian) "
                + "VALUES (?, ?, ?, ?, GETDATE())";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, log.getMaThanhVien());
            stmt.setString(2, log.getHanhDong());
            stmt.setString(3, log.getTruocKhiSua());
            stmt.setString(4, log.getSauKhiSua());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi ghi log lịch sử: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<LichSuThaoTac> getAll() {
        String sql = "SELECT * FROM LICHSUTHAOTAC ORDER BY thoiGian DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LichSuThaoTac.class));
    }
}
