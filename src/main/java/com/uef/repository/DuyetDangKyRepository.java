/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import com.uef.model.*;
import java.time.LocalDateTime;
import java.util.*;
import org.slf4j.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class DuyetDangKyRepository {

    private static final Logger logger = LoggerFactory.getLogger(DuyetDangKyRepository.class);
    private static final int PAGE_SIZE = 15;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DuyetDangKyRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<DuyetDangKy> layDanhSachDangKyCanDuyet(int page, String keyword, String location, String trangThai) {
        int offset = (page - 1) * PAGE_SIZE;
        if (offset < 0) {
            offset = 0;
            logger.warn("Offset calculated as negative, reset to 0. Page: {}", page);
        }
        String sql = "SELECT ddk.maDDK, dktg.maDKTG, dktg.maThanhVien, ddk.ngayDuyet, ddk.trangThaiDuyet, ddk.ghiChu, "
                + "tv.hoTen, tk.email, hd.maHoatDong, hd.tenHoatDong, hd.moTa, hd.thoiGianBatDau, hd.thoiGianKetThuc, "
                + "hd.diaDiem, hd.trangThai "
                + "FROM [DUYETDANGKY] ddk "
                + "INNER JOIN [DANGKYTHAMGIA] dktg ON ddk.maDKTG = dktg.maDKTG "
                + "LEFT JOIN [THANHVIEN] tv ON dktg.maThanhVien = tv.maThanhVien "
                + "LEFT JOIN [TAIKHOAN] tk ON tv.maThanhVien = tk.maTaiKhoan "
                + "LEFT JOIN [HOATDONG] hd ON dktg.maHoatDong = hd.maHoatDong "
                + "WHERE dktg.trangThai = N'Chờ duyệt' "
                + "AND hd.trangThai = N'Sắp diễn ra' "
                + "AND (:keyword IS NULL OR tv.hoTen LIKE '%' + :keyword + '%') "
                + "AND (:location IS NULL OR EXISTS (SELECT 1 FROM [HOATDONG] hd WHERE hd.maHoatDong = dktg.maHoatDong AND hd.diaDiem LIKE '%' + :location + '%')) "
                + "AND (:trangThai IS NULL OR ddk.trangThaiDuyet = :trangThai) "
                + "ORDER BY hd.thoiGianBatDau ASC OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";

        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", PAGE_SIZE);
        params.put("keyword", keyword);
        params.put("location", location);
        params.put("trangThai", trangThai);

        logger.info("Executing query for page: {}, offset: {}, limit: {}, keyword: {}, location: {}, trangThai: {}",
                page, offset, PAGE_SIZE, keyword, location, trangThai);
        List<DuyetDangKy> result = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            DuyetDangKy dk = new DuyetDangKy(
                    rs.getInt("maDDK"),
                    rs.getInt("maDKTG"),
                    rs.getInt("maThanhVien"),
                    rs.getObject("ngayDuyet", LocalDateTime.class),
                    rs.getString("trangThaiDuyet"),
                    rs.getString("ghiChu"),
                    rs.getString("hoTen"),
                    rs.getString("email"),
                    rs.getObject("maHoatDong") != null ? rs.getInt("maHoatDong") : null,
                    rs.getString("tenHoatDong"),
                    rs.getString("moTa"),
                    rs.getObject("thoiGianBatDau", LocalDateTime.class),
                    rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                    rs.getString("diaDiem"),
                    rs.getString("trangThai")
            );
            logger.debug("Row {}: maDDK={}, maDKTG={}, trangThaiDuyet={}, hoTen={}", rowNum, rs.getInt("maDDK"), rs.getInt("maDKTG"), rs.getString("trangThaiDuyet"), rs.getString("hoTen"));
            return dk;
        });
        logger.info("Retrieved {} records for page {}", result.size(), page);
        return result;
    }

    public int demTongSoDangKyCanDuyet(String keyword, String location, String trangThai) {
        String sql = "SELECT COUNT(*) FROM [DUYETDANGKY] ddk "
                + "INNER JOIN [DANGKYTHAMGIA] dktg ON ddk.maDKTG = dktg.maDKTG "
                + "LEFT JOIN [THANHVIEN] tv ON dktg.maThanhVien = tv.maThanhVien "
                + "LEFT JOIN [TAIKHOAN] tk ON tv.maThanhVien = tk.maTaiKhoan "
                + "LEFT JOIN [HOATDONG] hd ON dktg.maHoatDong = hd.maHoatDong "
                + "WHERE dktg.trangThai = N'Chờ duyệt' "
                + "AND hd.trangThai = N'Sắp diễn ra' "
                + "AND (:keyword IS NULL OR tv.hoTen LIKE '%' + :keyword + '%') "
                + "AND (:location IS NULL OR EXISTS (SELECT 1 FROM [HOATDONG] hd WHERE hd.maHoatDong = dktg.maHoatDong AND hd.diaDiem LIKE '%' + :location + '%')) "
                + "AND (:trangThai IS NULL OR ddk.trangThaiDuyet = :trangThai)";

        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("location", location);
        params.put("trangThai", trangThai);

        logger.info("Counting total records with query: {}, keyword: {}, location: {}, trangThai: {}", sql, keyword, location, trangThai);
        int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        logger.info("Total records counted: {}", count);
        return count;
    }

    public DuyetDangKy layDangKyCanDuyetTheoMa(int maDDK) {
        String sql = "SELECT ddk.maDDK, dktg.maDKTG, dktg.maThanhVien, ddk.ngayDuyet, ddk.trangThaiDuyet, ddk.ghiChu, "
                + "tv.hoTen, tk.email, hd.maHoatDong, hd.tenHoatDong, hd.moTa, hd.thoiGianBatDau, hd.thoiGianKetThuc, "
                + "hd.diaDiem, hd.trangThai "
                + "FROM [DUYETDANGKY] ddk "
                + "INNER JOIN [DANGKYTHAMGIA] dktg ON ddk.maDKTG = dktg.maDKTG "
                + "LEFT JOIN [THANHVIEN] tv ON dktg.maThanhVien = tv.maThanhVien "
                + "LEFT JOIN [TAIKHOAN] tk ON tv.maThanhVien = tk.maTaiKhoan "
                + "LEFT JOIN [HOATDONG] hd ON dktg.maHoatDong = hd.maHoatDong "
                + "WHERE ddk.maDDK = :maDDK AND dktg.trangThai = N'Chờ duyệt' "
                + "AND hd.trangThai = N'Sắp diễn ra'";
        Map<String, Object> params = new HashMap<>();
        params.put("maDDK", maDDK);
        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> new DuyetDangKy(
                rs.getInt("maDDK"),
                rs.getInt("maDKTG"),
                rs.getInt("maThanhVien"),
                rs.getObject("ngayDuyet", LocalDateTime.class),
                rs.getString("trangThaiDuyet"),
                rs.getString("ghiChu"),
                rs.getString("hoTen"),
                rs.getString("email"),
                rs.getObject("maHoatDong") != null ? rs.getInt("maHoatDong") : null,
                rs.getString("tenHoatDong"),
                rs.getString("moTa"),
                rs.getObject("thoiGianBatDau", LocalDateTime.class),
                rs.getObject("thoiGianKetThuc", LocalDateTime.class),
                rs.getString("diaDiem"),
                rs.getString("trangThai")
        ));
    }

    public void capNhatTrangThaiDuyet(int maDDK, String trangThaiDuyet, String ghiChu) {
        // Lấy maDKTG từ maDDK
        String getMaDKTGsql = "SELECT maDKTG FROM [DUYETDANGKY] WHERE maDDK = :maDDK";
        Map<String, Object> params = new HashMap<>();
        params.put("maDDK", maDDK);
        Integer maDKTG = namedParameterJdbcTemplate.queryForObject(getMaDKTGsql, params, Integer.class);

        // Cập nhật [DUYETDANGKY]
        String updateDuyetDangKySql = "UPDATE [DUYETDANGKY] SET trangThaiDuyet = :trangThaiDuyet, ngayDuyet = :ngayDuyet, ghiChu = :ghiChu WHERE maDDK = :maDDK";
        params.put("trangThaiDuyet", trangThaiDuyet);
        params.put("ngayDuyet", LocalDateTime.now());
        params.put("ghiChu", ghiChu != null ? ghiChu : "");
        int rowsAffectedDuyet = namedParameterJdbcTemplate.update(updateDuyetDangKySql, params);
        logger.info("Updated [DUYETDANGKY] for maDDK={}, rows affected: {}", maDDK, rowsAffectedDuyet);

        // Cập nhật [DANGKYTHAMGIA] (chỉ khi duyệt thành công)
        if ("Đã duyệt".equals(trangThaiDuyet) && maDKTG != null) {
            String updateDangKyThamGiaSql = "UPDATE [DANGKYTHAMGIA] SET trangThai = :trangThai WHERE maDKTG = :maDKTG";
            Map<String, Object> dangKyParams = new HashMap<>();
            dangKyParams.put("maDKTG", maDKTG);
            dangKyParams.put("trangThai", "Đã duyệt");
            int rowsAffectedDangKy = namedParameterJdbcTemplate.update(updateDangKyThamGiaSql, dangKyParams);
            logger.info("Updated [DANGKYTHAMGIA] for maDKTG={}, rows affected: {}", maDKTG, rowsAffectedDangKy);

            // Tao data bang diem danh
            if (rowsAffectedDangKy > 0) {
                // Lấy maHoatDong và maThanhVien từ maDKTG
                String selectInfoSql = "SELECT maHoatDong, maThanhVien FROM DANGKYTHAMGIA WHERE maDKTG = :maDKTG";
                Map<String, Object> infoParams = Map.of("maDKTG", maDKTG);
                Map<String, Object> info = namedParameterJdbcTemplate.queryForMap(selectInfoSql, infoParams);
                int maHoatDong = (int) info.get("maHoatDong");
                int maThanhVien = (int) info.get("maThanhVien");

                String getMaDiemDanhSql = "SELECT TOP 1 maDiemDanh FROM DIEMDANH WHERE maHoatDong = :maHoatDong ORDER BY maDiemDanh DESC";

                // Lấy maDiemDanh (nếu đã có)
                List<Integer> resultList = namedParameterJdbcTemplate.query(
                        getMaDiemDanhSql,
                        Map.of("maHoatDong", maHoatDong),
                        (rs, rowNum) -> rs.getInt("maDiemDanh")
                );
                int maDiemDanh;
                if (resultList.isEmpty()) {
                    // Chưa có → thêm mới DIEMDANH
                    String insertDiemDanhSql = "INSERT INTO DIEMDANH (maHoatDong, ghiNhanDiemDanh) VALUES (:maHoatDong, :ghiNhan)";
                    MapSqlParameterSource paramSource = new MapSqlParameterSource();
                    paramSource.addValue("maHoatDong", maHoatDong);
                    paramSource.addValue("ghiNhan", "Điểm danh cho sự kiện " + maHoatDong);

                    KeyHolder keyHolder = new GeneratedKeyHolder();
                    namedParameterJdbcTemplate.update(
                            insertDiemDanhSql,
                            paramSource,
                            keyHolder,
                            new String[]{"maDiemDanh"}
                    );
                    maDiemDanh = keyHolder.getKey().intValue();
                } else {
                    // Đã có → dùng kết quả
                    maDiemDanh = resultList.get(0);
                }

                // Thêm bản ghi PHIEUDIEMDANH
                String insertPhieuSql = "INSERT INTO PHIEUDIEMDANH (maDiemDanh, maThanhVien, maHoatDong, trangThai) VALUES (:maDiemDanh, :maThanhVien, :maHoatDong, :trangThai)";
                Map<String, Object> phieuParams = Map.of(
                        "maDiemDanh", maDiemDanh,
                        "maThanhVien", maThanhVien,
                        "maHoatDong", maHoatDong
                );
                namedParameterJdbcTemplate.update(insertPhieuSql, phieuParams);

                logger.info("Created PHIEUDIEMDANH for maThanhVien={}, maHoatDong={}", maThanhVien, maHoatDong);
            }
        }
        if ("Từ chối".equals(trangThaiDuyet) && maDKTG != null) {
            String updateDangKyThamGiaSql = "UPDATE [DANGKYTHAMGIA] SET trangThai = :trangThai WHERE maDKTG = :maDKTG";
            Map<String, Object> dangKyParams = new HashMap<>();
            dangKyParams.put("maDKTG", maDKTG);
            dangKyParams.put("trangThai", "Từ chối");
            int rowsAffectedDangKy = namedParameterJdbcTemplate.update(updateDangKyThamGiaSql, dangKyParams);
            logger.info("Updated [DANGKYTHAMGIA] for maDKTG={}, rows affected: {}", maDKTG, rowsAffectedDangKy);
        }
    }

    public boolean checkDangKy(int maThanhVien, int maHoatDong) {
        String sql = "select A.maThanhVien from DUYETDANGKY A "
                + "inner join DANGKYTHAMGIA B on A.maDKTG = B.maDKTG "
                + "WHERE A.trangThaiDuyet = N'Đã duyệt' "
                + "AND A.maThanhVien = :maThanhVien "
                + "AND and B.maHoatDong = :maHoatDong ";

        Map<String, Object> params = new HashMap<>();
        params.put("maThanhVien", maThanhVien);
        params.put("maHoatDong", maHoatDong);

        int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count > 0;
    }
}
