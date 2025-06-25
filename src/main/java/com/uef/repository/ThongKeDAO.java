/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import com.uef.model.ThongKe;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Asus
 */
@Repository
public class ThongKeDAO implements ThongKeRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Integer> getThongKeTheoThang(LocalDate from, LocalDate to, String status) {
        Map<String, Integer> result = new LinkedHashMap<>();
        StringBuilder sql = new StringBuilder("SELECT FORMAT(thoiGianBatDau, 'yyyy-MM') AS thang, COUNT(*) AS soLuong FROM HOATDONG WHERE 1=1");

        if (from != null) {
            sql.append(" AND thoiGianBatDau >= ?");
        }
        if (to != null) {
            sql.append(" AND thoiGianBatDau <= ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND trangThai = ?");
        }

        sql.append(" GROUP BY FORMAT(thoiGianBatDau, 'yyyy-MM') ORDER BY thang");

        List<Object> params = new java.util.ArrayList<>();
        if (from != null) {
            params.add(Date.valueOf(from));
        }
        if (to != null) {
            params.add(Date.valueOf(to));
        }
        if (status != null && !status.isEmpty()) {
            params.add(status);
        }

        jdbcTemplate.query(sql.toString(), params.toArray(), (rs) -> {
            result.put(rs.getString("thang"), rs.getInt("soLuong"));
        });

        return result;
    }

    @Override
    public Map<String, Integer> getThongKeTheoTrangThai(LocalDate from, LocalDate to, String status) {
        Map<String, Integer> result = new LinkedHashMap<>();
        StringBuilder sql = new StringBuilder("SELECT trangThai, COUNT(*) AS soLuong FROM HOATDONG WHERE 1=1");

        List<Object> params = new java.util.ArrayList<>();
        if (from != null) {
            sql.append(" AND thoiGianBatDau >= ?");
            params.add(Date.valueOf(from));
        }
        if (to != null) {
            sql.append(" AND thoiGianBatDau <= ?");
            params.add(Date.valueOf(to));
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND trangThai = ?");
            params.add(status);
        }

        sql.append(" GROUP BY trangThai");

        jdbcTemplate.query(sql.toString(), params.toArray(), (rs) -> {
            result.put(rs.getString("trangThai"), rs.getInt("soLuong"));
        });

        return result;
    }

    private int countByStatus(LocalDate from, LocalDate to, String status) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM HOATDONG WHERE 1=1");
        List<Object> params = new java.util.ArrayList<>();

        if (from != null) {
            sql.append(" AND thoiGianBatDau >= ?");
            params.add(Date.valueOf(from));
        }
        if (to != null) {
            sql.append(" AND thoiGianBatDau <= ?");
            params.add(Date.valueOf(to));
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND trangThai = ?");
            params.add(status);
        }

        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    @Override
    public int getTongSoHoatDong(LocalDate from, LocalDate to, String status) {
        return countByStatus(from, to, status);
    }

    @Override
    public int getSoHoatDongThangTruoc(String status) {
        LocalDate from = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        LocalDate to = LocalDate.now().withDayOfMonth(1).minusDays(1);
        return countByStatus(from, to, status);
    }

    @Override
    public int getSoKetThuc(LocalDate from, LocalDate to) {
        return countByStatus(from, to, "Đã kết thúc");
    }

    @Override
    public int getSoKetThucThangTruoc() {
        LocalDate from = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        LocalDate to = LocalDate.now().withDayOfMonth(1).minusDays(1);
        return countByStatus(from, to, "Đã kết thúc");
    }

    @Override
    public int getSoDangHoatDong(LocalDate from, LocalDate to) {
        return countByStatus(from, to, "Đang hoạt động");
    }

    @Override
    public int getSoDangHoatDongThangTruoc() {
        LocalDate from = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        LocalDate to = LocalDate.now().withDayOfMonth(1).minusDays(1);
        return countByStatus(from, to, "Đang hoạt động");
    }

    @Override
    public int getSoSapDienRa(LocalDate from, LocalDate to) {
        return countByStatus(from, to, "Sắp diễn ra");
    }

    @Override
    public int getSoSapDienRaThangTruoc() {
        LocalDate from = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        LocalDate to = LocalDate.now().withDayOfMonth(1).minusDays(1);
        return countByStatus(from, to, "Sắp diễn ra");
    }

    @Override
    public List<ThongKe> getDanhSachHoatDong(LocalDate from, LocalDate to, String status) {
        StringBuilder sql = new StringBuilder("SELECT maHoatDong, tenHoatDong, thoiGianBatDau, thoiGianKetThuc, diaDiem, trangThai FROM HOATDONG WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (from != null) {
            sql.append(" AND thoiGianBatDau >= ?");
            params.add(Date.valueOf(from));
        }
        if (to != null) {
            sql.append(" AND thoiGianBatDau <= ?");
            params.add(Date.valueOf(to));
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND trangThai = ?");
            params.add(status);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
            ThongKe tk = new ThongKe();
            tk.setMaHoatDong(String.valueOf(rs.getInt("maHoatDong")));
            tk.setTenHoatDong(rs.getString("tenHoatDong"));
            tk.setNgayBatDau(rs.getDate("thoiGianBatDau"));
            tk.setNgayKetThuc(rs.getDate("thoiGianKetThuc"));
            tk.setDiaDiem(rs.getString("diaDiem"));
            tk.setTrangThaiHoatDong(rs.getString("trangThai"));
            return tk;
        });
    }

    //tnv
    @Override
    public List<ThongKe> getThongKeTNVTheoHoatDong(LocalDate from, LocalDate to, String status) {
        StringBuilder sql = new StringBuilder(
                "SELECT hd.maHoatDong, hd.tenHoatDong, hd.thoiGianBatDau, hd.thoiGianKetThuc, "
                + "hd.diaDiem, hd.trangThai AS trangThaiHoatDong, "
                + "COUNT(DISTINCT dktg.maThanhVien) AS soLuongTNV "
                + "FROM HOATDONG hd "
                + "LEFT JOIN DANGKYTHAMGIA dktg ON hd.maHoatDong = dktg.maHoatDong AND dktg.trangThai = N'Đã duyệt' "
                + "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();
        if (from != null) {
            sql.append("AND hd.thoiGianBatDau >= ? ");
            params.add(Date.valueOf(from));
        }
        if (to != null) {
            sql.append("AND hd.thoiGianBatDau <= ? ");
            params.add(Date.valueOf(to));
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND hd.trangThai = ? ");
            params.add(status);
        }

        sql.append("GROUP BY hd.maHoatDong, hd.tenHoatDong, hd.thoiGianBatDau, hd.thoiGianKetThuc, hd.diaDiem, hd.trangThai ");
        sql.append("ORDER BY hd.thoiGianBatDau DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
            ThongKe tk = new ThongKe();
            tk.setMaHoatDong(String.valueOf(rs.getInt("maHoatDong")));
            tk.setTenHoatDong(rs.getString("tenHoatDong"));
            tk.setNgayBatDau(rs.getDate("thoiGianBatDau"));
            tk.setNgayKetThuc(rs.getDate("thoiGianKetThuc"));
            tk.setDiaDiem(rs.getString("diaDiem"));
            tk.setTrangThaiHoatDong(rs.getString("trangThaiHoatDong"));
            tk.setSoLuongTNV(rs.getInt("soLuongTNV"));
            return tk;
        });
    }

    @Override
    public int getTongTNV() {
        String sql = "SELECT COUNT(*) FROM THANHVIEN";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public int getTongTNVThamGia(LocalDate from, LocalDate to) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(DISTINCT dd.maThanhVien) FROM DUYETDANGKY dd "
                + "JOIN DANGKYTHAMGIA dk ON dd.maDKTG = dk.maDKTG "
                + "JOIN HOATDONG h ON dk.maHoatDong = h.maHoatDong "
                + "WHERE dd.trangThaiDuyet = N'Đã duyệt' "
        );

        List<Object> params = new ArrayList<>();
        if (from != null) {
            sql.append(" AND h.thoiGianBatDau >= ?");
            params.add(Date.valueOf(from));
        }
        if (to != null) {
            sql.append(" AND h.thoiGianBatDau <= ?");
            params.add(Date.valueOf(to));
        }

        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    @Override
    public ThongKe getThongKeTongHopTNV(LocalDate from, LocalDate to) {
        ThongKe tk = new ThongKe();
        tk.setTongTNV(getTongTNV());
        tk.setTongTNVThamGia(getTongTNVThamGia(from, to));
        return tk;
    }

    @Override
    public int getTongSoTNVThamGiaTheoHoatDong(LocalDate from, LocalDate to, String status) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(DISTINCT dktg.maThanhVien) "
                + "FROM HOATDONG hd "
                + "LEFT JOIN DANGKYTHAMGIA dktg ON hd.maHoatDong = dktg.maHoatDong AND dktg.trangThai = N'Đã duyệt' "
                + "WHERE 1=1 ");

        List<Object> params = new ArrayList<>();
        if (from != null) {
            sql.append("AND hd.thoiGianBatDau >= ? ");
            params.add(Date.valueOf(from));
        }
        if (to != null) {
            sql.append("AND hd.thoiGianBatDau <= ? ");
            params.add(Date.valueOf(to));
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND hd.trangThai = ? ");
            params.add(status);
        }

        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    @Override
    public int getSoLuongTNVThucTeTheoHoatDong(LocalDate from, LocalDate to) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(DISTINCT dktg.maThanhVien) "
                + "FROM HOATDONG hd "
                + "JOIN DANGKYTHAMGIA dktg ON hd.maHoatDong = dktg.maHoatDong "
                + "WHERE dktg.trangThai = N'Đã duyệt' "
        );

        List<Object> params = new ArrayList<>();
        if (from != null) {
            sql.append(" AND hd.thoiGianBatDau >= ?");
            params.add(Date.valueOf(from));
        }
        if (to != null) {
            sql.append(" AND hd.thoiGianBatDau <= ?");
            params.add(Date.valueOf(to));
        }

        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }
    

}
