package com.uef.repository;

import com.uef.model.LichSuThaoTac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LichSuThaoTacDAO implements LichSuRepo {

    private final DataSource dataSource;

    public LichSuThaoTacDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<LichSuThaoTac> getAll() {
        String sql = "SELECT * FROM [LichSuThaoTac] ORDER BY thoiGian DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LichSuThaoTac.class));
    }

    @Override
    public boolean save(LichSuThaoTac log) {
        String sql = "INSERT INTO LichSuThaoTac (maTNV, hanhDong, truocKhiSua, sauKhiSua, thoiGian) VALUES (?, ?, ?, ?, GETDATE())";

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, log.getMaTNV());
            stmt.setString(2, log.getHanhDong());
            stmt.setString(3, log.getTruocKhiSua());
            stmt.setString(4, log.getSauKhiSua());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
