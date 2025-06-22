/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Asus
 */
@Repository
public class ThongKeDAO implements ThongKeRepo {

    @Autowired
    private DataSource dataSource;

    @Override
    public Map<String, Integer> getThongKeTheoThang(LocalDate from, LocalDate to, String status) {
        Map<String, Integer> result = new LinkedHashMap<>();
        StringBuilder sql = new StringBuilder("SELECT FORMAT(thoiGianBatDau, 'yyyy-MM') AS thang, COUNT(*) AS soLuong FROM HOATDONG WHERE 1=1 ");
        if (from != null) {
            sql.append(" AND thoiGianBatDau >= ? ");
        }
        if (to != null) {
            sql.append(" AND thoiGianBatDau <= ? ");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND trangThai = ? ");
        }
        sql.append(" GROUP BY FORMAT(thoiGianBatDau, 'yyyy-MM') ORDER BY thang");

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (from != null) {
                ps.setDate(index++, Date.valueOf(from));
            }
            if (to != null) {
                ps.setDate(index++, Date.valueOf(to));
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("thang"), rs.getInt("soLuong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Integer> getThongKeTheoTrangThai(LocalDate from, LocalDate to, String status) {
        Map<String, Integer> result = new LinkedHashMap<>();
        StringBuilder sql = new StringBuilder("SELECT trangThai, COUNT(*) AS soLuong FROM HOATDONG WHERE 1=1 ");
        if (from != null) {
            sql.append(" AND thoiGianBatDau >= ? ");
        }
        if (to != null) {
            sql.append(" AND thoiGianBatDau <= ? ");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND trangThai = ? ");
        }
        sql.append(" GROUP BY trangThai");

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (from != null) {
                ps.setDate(index++, Date.valueOf(from));
            }
            if (to != null) {
                ps.setDate(index++, Date.valueOf(to));
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("trangThai"), rs.getInt("soLuong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getTongSoHoatDong(LocalDate from, LocalDate to, String status) {
        return countWithCondition(from, to, status, null);
    }

    @Override
    public int getSoHoatDongThangTruoc(String status) {
        LocalDate now = LocalDate.now().withDayOfMonth(1);
        LocalDate from = now.minusMonths(1);
        LocalDate to = now.minusDays(1);
        return countWithCondition(from, to, status, null);
    }

    @Override
    public int getSoHoanThanh(LocalDate from, LocalDate to) {
        return countWithCondition(from, to, "Hoàn thành", null);
    }

    @Override
    public int getSoHoanThanhThangTruoc() {
        LocalDate now = LocalDate.now().withDayOfMonth(1);
        return countWithCondition(now.minusMonths(1), now.minusDays(1), "Hoàn thành", null);
    }

    @Override
    public int getSoDangThucHien(LocalDate from, LocalDate to) {
        return countWithCondition(from, to, "Đang thực hiện", null);
    }

    @Override
    public int getSoDangThucHienThangTruoc() {
        LocalDate now = LocalDate.now().withDayOfMonth(1);
        return countWithCondition(now.minusMonths(1), now.minusDays(1), "Đang thực hiện", null);
    }

    @Override
    public int getSoDaHuy(LocalDate from, LocalDate to) {
        return countWithCondition(from, to, "Đã hủy", null);
    }

    @Override
    public int getSoDaHuyThangTruoc() {
        LocalDate now = LocalDate.now().withDayOfMonth(1);
        return countWithCondition(now.minusMonths(1), now.minusDays(1), "Đã hủy", null);
    }

    private int countWithCondition(LocalDate from, LocalDate to, String status, String extra) {
        int result = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM HOATDONG WHERE 1=1 ");
        if (from != null) {
            sql.append(" AND thoiGianBatDau >= ? ");
        }
        if (to != null) {
            sql.append(" AND thoiGianBatDau <= ? ");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND trangThai = ? ");
        }
        if (extra != null) {
            sql.append(" AND ").append(extra);
        }

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (from != null) {
                ps.setDate(index++, Date.valueOf(from));
            }
            if (to != null) {
                ps.setDate(index++, Date.valueOf(to));
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Integer> getThongKeTNVTheoHoatDong(LocalDate from, LocalDate to) {
        Map<String, Integer> result = new LinkedHashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT hd.tenHoatDong, COUNT(DISTINCT d.maTNV) AS soTNV "
                + "FROM DANGKY d JOIN HOATDONG hd ON d.maHoatDong = hd.maHoatDong "
                + "WHERE 1=1 ");

        if (from != null) {
            sql.append(" AND hd.thoiGianBatDau >= ? ");
        }
        if (to != null) {
            sql.append(" AND hd.thoiGianBatDau <= ? ");
        }
        sql.append(" GROUP BY hd.tenHoatDong ORDER BY hd.tenHoatDong");

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (from != null) {
                ps.setDate(index++, Date.valueOf(from));
            }
            if (to != null) {
                ps.setDate(index++, Date.valueOf(to));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("tenHoatDong"), rs.getInt("soTNV"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getTongTNVThamGia(LocalDate from, LocalDate to) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(DISTINCT d.maTNV) "
                + "FROM DANGKY d JOIN HOATDONG h ON d.maHoatDong = h.maHoatDong WHERE 1=1 ");
        if (from != null) {
            sql.append(" AND h.thoiGianBatDau >= ? ");
        }
        if (to != null) {
            sql.append(" AND h.thoiGianBatDau <= ? ");
        }

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (from != null) {
                ps.setDate(index++, Date.valueOf(from));
            }
            if (to != null) {
                ps.setDate(index++, Date.valueOf(to));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getTongTNVThangTruoc() {
        LocalDate now = LocalDate.now().withDayOfMonth(1);
        LocalDate from = now.minusMonths(1);
        LocalDate to = now.minusDays(1);
        return getTongTNVThamGia(from, to);
    }

    @Override
    public Map<String, Integer> getThongKeTNVTheoThang(LocalDate from, LocalDate to) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getTongTNV(LocalDate from, LocalDate to) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}