/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

import com.uef.model.SuKien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.*;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SuKienService {

    private static final Logger logger = LoggerFactory.getLogger(SuKienService.class);
    public static final int PAGE_SIZE = 12; // Số sự kiện mỗi trang
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        logger.debug("DataSource đã được thiết lập: {}", dataSource);
    }

    // Thêm sự kiện mới
    @Transactional
    public void themSuKien(SuKien suKien) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (suKien.getTenSuKien() == null || suKien.getTenSuKien().isEmpty()) {
                throw new IllegalArgumentException("Tên sự kiện không được để trống");
            }
            if (suKien.getThoiGianBatDau() == null) {
                suKien.setThoiGianBatDau(LocalDateTime.now());
            }
            if (suKien.getTrangThai() == null) {
                suKien.setTrangThai("Chưa bắt đầu"); // Giá trị mặc định
            }

            String sql = "INSERT INTO [Sự Kiện] (tenSuKien, moTa, thoiGianBatDau, thoiGianKetThuc, diaDiem, trangThai) VALUES (?, ?, ?, ?, ?, ?)";
            int rowsAffected = jdbcTemplate.update(sql,
                    suKien.getTenSuKien(),
                    suKien.getMoTa(),
                    suKien.getThoiGianBatDau(),
                    suKien.getThoiGianKetThuc(),
                    suKien.getDiaDiem(),
                    suKien.getTrangThai()
            );
            logger.info("Thêm sự kiện thành công, số hàng ảnh hưởng: {}", rowsAffected);
        } catch (Exception e) {
            logger.error("Lỗi khi thêm sự kiện: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể thêm sự kiện vào CSDL", e);
        }
    }

    // Cập nhật sự kiện
    @Transactional
    public void capNhatSuKien(SuKien suKien) {
        try {
            String sql = "UPDATE [Sự Kiện] SET tenSuKien = ?, moTa = ?, thoiGianBatDau = ?, thoiGianKetThuc = ?, diaDiem = ?, trangThai = ? WHERE maSuKien = ?";
            int rowsAffected = jdbcTemplate.update(sql,
                    suKien.getTenSuKien(),
                    suKien.getMoTa(),
                    suKien.getThoiGianBatDau(),
                    suKien.getThoiGianKetThuc(),
                    suKien.getDiaDiem(),
                    suKien.getTrangThai(),
                    suKien.getMaSuKien()
            );
            logger.info("Cập nhật sự kiện thành công, số hàng ảnh hưởng: {}", rowsAffected);
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật sự kiện: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể cập nhật sự kiện", e);
        }
    }

    // Xóa sự kiện
    @Transactional
    public void xoaSuKien(int maSuKien) {
        try {
            String sql = "DELETE FROM [Sự Kiện] WHERE maSuKien = ?";
            int rowsAffected = jdbcTemplate.update(sql, maSuKien);
            logger.info("Xóa sự kiện thành công, số hàng ảnh hưởng: {}", rowsAffected);
        } catch (Exception e) {
            logger.error("Lỗi khi xóa sự kiện: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể xóa sự kiện", e);
        }
    }

    // Lấy danh sách tất cả sự kiện
    public List<SuKien> layDanhSachSuKien() {
        try {
            String sql = "SELECT * FROM [Sự Kiện]";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new SuKien(
                    rs.getInt("maSuKien"),
                    rs.getString("tenSuKien"),
                    rs.getString("moTa"),
                    rs.getObject("thoiGianBatDau", LocalDateTime.class),
                    rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                    rs.getString("diaDiem"),
                    rs.getString("trangThai")
            ));
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách sự kiện: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể lấy danh sách sự kiện", e);
        }
    }

    // Lấy danh sách sự kiện theo trang
    public List<SuKien> layDanhSachSuKienTheoTrang(int page) {
        try {
            int offset = (page - 1) * PAGE_SIZE;
            String sql = "SELECT * FROM [Sự Kiện] ORDER BY thoiGianBatDau DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            return jdbcTemplate.query(sql, new Object[]{offset, PAGE_SIZE}, (rs, rowNum) -> new SuKien(
                    rs.getInt("maSuKien"),
                    rs.getString("tenSuKien"),
                    rs.getString("moTa"),
                    rs.getObject("thoiGianBatDau", LocalDateTime.class),
                    rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                    rs.getString("diaDiem"),
                    rs.getString("trangThai")
            ));
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách sự kiện theo trang {}: {}", page, e.getMessage(), e);
            throw new RuntimeException("Không thể lấy danh sách sự kiện theo trang", e);
        }
    }

    // Đếm tổng số sự kiện
    public int demTongSoSuKien() {
        try {
            String sql = "SELECT COUNT(*) FROM [Sự Kiện]";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            logger.error("Lỗi khi đếm tổng số sự kiện: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể đếm tổng số sự kiện", e);
        }
    }

    // Lấy sự kiện theo mã
    public SuKien laySuKienTheoMa(int maSuKien) {
        try {
            String sql = "SELECT * FROM [Sự Kiện] WHERE maSuKien = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{maSuKien}, (rs, rowNum) -> new SuKien(
                    rs.getInt("maSuKien"),
                    rs.getString("tenSuKien"),
                    rs.getString("moTa"),
                    rs.getObject("thoiGianBatDau", LocalDateTime.class),
                    rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                    rs.getString("diaDiem"),
                    rs.getString("trangThai")
            ));
        } catch (Exception e) {
            logger.error("Lỗi khi lấy sự kiện theo mã {}: {}", maSuKien, e.getMessage(), e);
            throw new RuntimeException("Không thể lấy sự kiện", e);
        }
    }
}
