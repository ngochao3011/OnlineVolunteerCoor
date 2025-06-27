/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

import com.uef.model.HoatDong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.*;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HoatDongService {

    private static final Logger logger = LoggerFactory.getLogger(HoatDongService.class);
    public static final int PAGE_SIZE = 12; // Số hoạt động mỗi trang
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        logger.debug("DataSource đã được thiết lập: {}", dataSource);
    }

    // Thêm hoạt động mới
    @Transactional
    public void themHoatDong(HoatDong hoatDong) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (hoatDong.getTenHoatDong() == null || hoatDong.getTenHoatDong().isEmpty()) {
                throw new IllegalArgumentException("Tên hoạt động không được để trống");
            }
            if (hoatDong.getThoiGianBatDau() == null) {
                hoatDong.setThoiGianBatDau(LocalDateTime.now());
            }
            if (hoatDong.getTrangThai() == null) {
                hoatDong.setTrangThai("Chưa bắt đầu"); // Giá trị mặc định
            }

            String sql = "INSERT INTO [HOATDONG] (tenHoatDong, moTa, thoiGianBatDau, thoiGianKetThuc, diaDiem, trangThai) VALUES (?, ?, ?, ?, ?, ?)";
            int rowsAffected = jdbcTemplate.update(sql,
                    hoatDong.getTenHoatDong(),
                    hoatDong.getMoTa(),
                    hoatDong.getThoiGianBatDau(),
                    hoatDong.getThoiGianKetThuc(),
                    hoatDong.getDiaDiem(),
                    hoatDong.getTrangThai()
            );
            logger.info("Thêm hoạt động thành công, số hàng ảnh hưởng: {}", rowsAffected);
        } catch (Exception e) {
            logger.error("Lỗi khi thêm hoạt động: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể thêm hoạt động vào CSDL", e);
        }
    }

    // Cập nhật hoạt động
    @Transactional
    public void capNhatHoatDong(HoatDong hoatDong) {
        try {
            String sql = "UPDATE [HOATDONG] SET tenHoatDong = ?, moTa = ?, thoiGianBatDau = ?, thoiGianKetThuc = ?, diaDiem = ?, trangThai = ? WHERE maHoatDong = ?";
            int rowsAffected = jdbcTemplate.update(sql,
                    hoatDong.getTenHoatDong(),
                    hoatDong.getMoTa(),
                    hoatDong.getThoiGianBatDau(),
                    hoatDong.getThoiGianKetThuc(),
                    hoatDong.getDiaDiem(),
                    hoatDong.getTrangThai(),
                    hoatDong.getMaHoatDong()
            );
            logger.info("Cập nhật hoạt động thành công, số hàng ảnh hưởng: {}", rowsAffected);
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật hoạt động: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể cập nhật hoạt động", e);
        }
    }

    // Xóa hoạt động
    @Transactional
    public void xoaHoatDong(int maHoatDong) {
        try {
            // Delete related records from child tables to avoid foreign key constraint violations
            // Order matters: delete from child tables first, then parent tables
            
            // 1. Delete from PHIEUDANHGIA (references HOATDONG)
            String sqlDeletePhieuDanhGia = "DELETE FROM [PHIEUDANHGIA] WHERE maHoatDong = ?";
            jdbcTemplate.update(sqlDeletePhieuDanhGia, maHoatDong);
            
            // 2. Delete from PHIEUDIEMDANH (references DIEMDANH and HOATDONG)
            // First delete records that reference DIEMDANH records for this HOATDONG
            String sqlDeletePhieuDiemDanhByDiemDanh = "DELETE FROM [PHIEUDIEMDANH] WHERE maDiemDanh IN (SELECT maDiemDanh FROM [DIEMDANH] WHERE maHoatDong = ?)";
            jdbcTemplate.update(sqlDeletePhieuDiemDanhByDiemDanh, maHoatDong);
            
            // Then delete records that directly reference HOATDONG
            String sqlDeletePhieuDiemDanhByHoatDong = "DELETE FROM [PHIEUDIEMDANH] WHERE maHoatDong = ?";
            jdbcTemplate.update(sqlDeletePhieuDiemDanhByHoatDong, maHoatDong);
            
            // 3. Delete from DIEMDANH (references HOATDONG)
            String sqlDeleteDiemDanh = "DELETE FROM [DIEMDANH] WHERE maHoatDong = ?";
            jdbcTemplate.update(sqlDeleteDiemDanh, maHoatDong);
            
            // 4. Delete from DANGKYTHAMGIA (references HOATDONG)
            String sqlDeleteDangKy = "DELETE FROM [DANGKYTHAMGIA] WHERE maHoatDong = ?";
            jdbcTemplate.update(sqlDeleteDangKy, maHoatDong);
            
            // 5. Delete from HINHANH (references HOATDONG)
            String sqlDeleteHinhAnh = "DELETE FROM [HINHANH] WHERE maHoatDong = ?";
            jdbcTemplate.update(sqlDeleteHinhAnh, maHoatDong);
            
            // 6. Finally, delete from HOATDONG (main table)
            String sql = "DELETE FROM [HOATDONG] WHERE maHoatDong = ?";
            int rowsAffected = jdbcTemplate.update(sql, maHoatDong);
            logger.info("Xóa hoạt động thành công, số hàng ảnh hưởng: {}", rowsAffected);
        } catch (Exception e) {
            logger.error("Lỗi khi xóa hoạt động: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể xóa hoạt động", e);
        }
    }

    // Lấy danh sách tất cả hoạt động
    public List<HoatDong> layDanhSachHoatDong() {
        try {
            String sql = "SELECT * FROM HOATDONG";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new HoatDong(
                    rs.getInt("maHoatDong"),
                    rs.getString("tenHoatDong"),
                    rs.getString("moTa"),
                    rs.getObject("thoiGianBatDau", LocalDateTime.class),
                    rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                    rs.getString("diaDiem"),
                    rs.getString("trangThai")
            ));
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách hoạt động: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể lấy danh sách hoạt động", e);
        }
    }

    // Lấy danh sách hoạt động theo trang
    public List<HoatDong> layDanhSachHoatDongTheoTrang(int page) {
        try {
            int offset = (page - 1) * PAGE_SIZE;
            String sql = "SELECT * FROM HOATDONG ORDER BY thoiGianBatDau DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            return jdbcTemplate.query(sql, new Object[]{offset, PAGE_SIZE}, (rs, rowNum) -> new HoatDong(
                    rs.getInt("maHoatDong"),
                    rs.getString("tenHoatDong"),
                    rs.getString("moTa"),
                    rs.getObject("thoiGianBatDau", LocalDateTime.class),
                    rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                    rs.getString("diaDiem"),
                    rs.getString("trangThai")
            ));
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách hoạt động theo trang {}: {}", page, e.getMessage(), e);
            throw new RuntimeException("Không thể lấy danh sách hoạt động theo trang", e);
        }
    }

    // Đếm tổng số hoạt động
    public int demTongSoHoatDong() {
        try {
            String sql = "SELECT COUNT(*) FROM HOATDONG";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            logger.error("Lỗi khi đếm tổng số hoạt động: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể đếm tổng số hoạt động", e);
        }
    }

    public List<HoatDong> timKiemVaPhanTrang(String keyword, String location, String trangThai, int page) {
        StringBuilder sql = new StringBuilder("SELECT * FROM HOATDONG WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND tenHoatDong LIKE ?");
            params.add("%" + keyword + "%");
        }
        if (location != null && !location.isEmpty()) {
            sql.append(" AND diaDiem LIKE ?");
            params.add("%" + location + "%");
        }
        if (trangThai != null && !trangThai.isEmpty()) {
            sql.append(" AND trangThai = ?");
            params.add(trangThai);
        }

        int offset = (page - 1) * PAGE_SIZE;
        sql.append(" ORDER BY thoiGianBatDau DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add(offset);
        params.add(PAGE_SIZE);

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new HoatDong(
                rs.getInt("maHoatDong"),
                rs.getString("tenHoatDong"),
                rs.getString("moTa"),
                rs.getObject("thoiGianBatDau", LocalDateTime.class),
                rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                rs.getString("diaDiem"),
                rs.getString("trangThai")
        ));
    }

    public int demTongSoHoatDongTimKiem(String keyword, String location, String trangThai) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM HOATDONG WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND tenHoatDong LIKE ?");
            params.add("%" + keyword + "%");
        }
        if (location != null && !location.isEmpty()) {
            sql.append(" AND diaDiem LIKE ?");
            params.add("%" + location + "%");
        }
        if (trangThai != null && !trangThai.isEmpty()) {
            sql.append(" AND trangThai = ?");
            params.add(trangThai);
        }

        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    // Lấy hoạt động theo mã
    public HoatDong layHoatDongTheoMa(int maHoatDong) {
        try {
            String sql = "SELECT * FROM HOATDONG WHERE maHoatDong = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{maHoatDong}, (rs, rowNum) -> new HoatDong(
                    rs.getInt("maHoatDong"),
                    rs.getString("tenHoatDong"),
                    rs.getString("moTa"),
                    rs.getObject("thoiGianBatDau", LocalDateTime.class),
                    rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                    rs.getString("diaDiem"),
                    rs.getString("trangThai")
            ));
        } catch (Exception e) {
            logger.error("Lỗi khi lấy hoạt động theo mã {}: {}", maHoatDong, e.getMessage(), e);
            throw new RuntimeException("Không thể lấy hoạt động", e);
        }
    }

    public List<HoatDong> layHoatDongNoiBat() {
        try {
            String sql = "SELECT TOP 3 * FROM HOATDONG WHERE trangThai = N'Sắp diễn ra' ORDER BY thoiGianBatDau DESC";
            List<HoatDong> result = jdbcTemplate.query(sql, (rs, rowNum) -> new HoatDong(
                    rs.getInt("maHoatDong"),
                    rs.getString("tenHoatDong"),
                    rs.getString("moTa"),
                    rs.getObject("thoiGianBatDau", LocalDateTime.class),
                    rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                    rs.getString("diaDiem"),
                    rs.getString("trangThai")
            ));
            System.out.println("Số hoạt động nổi bật trả về: " + result.size());
            return result;
        } catch (Exception e) {
            logger.error("Lỗi khi lấy hoạt động nổi bật: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể lấy danh sách hoạt động nổi bật", e);
        }
    }
}
