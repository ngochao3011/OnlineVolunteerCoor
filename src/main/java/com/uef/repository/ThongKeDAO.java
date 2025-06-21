/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

/**
 *
 * @author Asus
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class ThongKeDAO implements ThongKeRepo {

    private final DataSource dataSource;

    public ThongKeDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 1. Biểu đồ theo tháng
    @Override
    public Map<String, Integer> getThongKeTheoThang() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT FORMAT(thoiGianBatDau, 'yyyy-MM') AS thang, COUNT(*) AS soLuong "
                + "FROM HOATDONG GROUP BY FORMAT(thoiGianBatDau, 'yyyy-MM') ORDER BY thang";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("thang"), rs.getInt("soLuong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 2. Thống kê trạng thái hiện tại
    @Override
    public Map<String, Integer> getThongKeTheoTrangThai() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT trangThai, COUNT(*) AS soLuong FROM HOATDONG GROUP BY trangThai";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("trangThai"), rs.getInt("soLuong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 3. Tổng số hoạt động hiện tại (tháng này)
    @Override
    public int getTongSoHoatDong() {
        return countBySQL("SELECT COUNT(*) FROM HOATDONG WHERE MONTH(thoiGianBatDau) = MONTH(GETDATE()) AND YEAR(thoiGianBatDau) = YEAR(GETDATE())");
    }

    @Override
    public int getSoHoatDongThangTruoc() {
        return countBySQL("SELECT COUNT(*) FROM HOATDONG WHERE MONTH(thoiGianBatDau) = MONTH(DATEADD(MONTH, -1, GETDATE())) AND YEAR(thoiGianBatDau) = YEAR(DATEADD(MONTH, -1, GETDATE()))");
    }

    // 4. Thống kê theo trạng thái tháng này / tháng trước
    @Override
    public int getSoHoanThanh() {
        return getSoTrangThaiTheoThang("Hoàn thành", 0);
    }

    @Override
    public int getSoHoanThanhThangTruoc() {
        return getSoTrangThaiTheoThang("Hoàn thành", -1);
    }

    @Override
    public int getSoDangThucHien() {
        return getSoTrangThaiTheoThang("Đang thực hiện", 0);
    }

    @Override
    public int getSoDangThucHienThangTruoc() {
        return getSoTrangThaiTheoThang("Đang thực hiện", -1);
    }

    @Override
    public int getSoDaHuy() {
        return getSoTrangThaiTheoThang("Đã hủy", 0);
    }

    @Override
    public int getSoDaHuyThangTruoc() {
        return getSoTrangThaiTheoThang("Đã hủy", -1);
    }

    // 5. Dành cho lọc từ-to
    @Override
    public int getSoHoatDongBetween(String from, String to) {
        String sql = "SELECT COUNT(*) FROM HOATDONG WHERE thoiGianBatDau BETWEEN ? AND ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, from);
            stmt.setString(2, to);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getSoTrangThaiBetween(String trangThai, String from, String to) {
        String sql = "SELECT COUNT(*) FROM HOATDONG WHERE trangThai = ? AND thoiGianBatDau BETWEEN ? AND ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            stmt.setString(2, from);
            stmt.setString(3, to);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ====== HÀM HỖ TRỢ CHUNG ======
    private int countBySQL(String sql) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getSoTrangThaiTheoThang(String trangThai, int offsetMonth) {
        String sql = "SELECT COUNT(*) FROM HOATDONG "
                + "WHERE trangThai = ? "
                + "AND MONTH(thoiGianBatDau) = MONTH(DATEADD(MONTH, ?, GETDATE())) "
                + "AND YEAR(thoiGianBatDau) = YEAR(DATEADD(MONTH, ?, GETDATE()))";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            stmt.setInt(2, offsetMonth);
            stmt.setInt(3, offsetMonth);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
