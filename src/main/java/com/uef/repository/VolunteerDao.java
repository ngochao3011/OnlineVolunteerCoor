package com.uef.repository;

import com.uef.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.text.SimpleDateFormat;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VolunteerDao implements VolunteerRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Volunteer> getAll(int page, int pageSize) {
        String sql = "SELECT * FROM [THANHVIEN] ORDER BY maThanhVien OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = (page - 1) * pageSize;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Volunteer.class), offset, pageSize);
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM [THANHVIEN]";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Volunteer getById(int id) {
        String sql = "SELECT * FROM [THANHVIEN] WHERE maThanhVien = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Volunteer.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean save(Volunteer volunteer) {
        String sql = "INSERT INTO [THANHVIEN] (maThanhVien, hoTen, sdt, diaChi, trangThai, ngayDangKy, urlAvatar, chucVu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ngayDangKy = volunteer.getNgayDangKy() != null ? sdf.format(volunteer.getNgayDangKy()) : null;

        return jdbcTemplate.update(sql,
                volunteer.getMaThanhVien(),
                volunteer.getHoTen(),
                volunteer.getSdt(),
                volunteer.getDiaChi(),
                volunteer.getTrangThai(),
                ngayDangKy,
                volunteer.getUrlAvatar(),
                volunteer.getChucVu()) > 0;
    }

    @Override
    public boolean update(Volunteer v) {
        if (v.getHoTen() == null || v.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống.");
        }
        String sql = "UPDATE [THANHVIEN] SET hoTen=?, sdt=?, diaChi=?, trangThai=?, ngayDangKy=?, urlAvatar=?, chucVu=? WHERE maThanhVien=?";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ngayDangKy = v.getNgayDangKy() != null ? sdf.format(v.getNgayDangKy()) : null;

        return jdbcTemplate.update(sql,
                v.getHoTen(),
                v.getSdt(),
                v.getDiaChi(),
                v.getTrangThai(),
                ngayDangKy,
                v.getUrlAvatar(),
                v.getChucVu(),
                v.getMaThanhVien()) > 0;
    }

    @Override
    @Transactional
    public boolean delete(int maThanhVien) {
        try {
            // Thực hiện xóa bản ghi trong bảng THANHVIEN
            String deleteThanhVienSql = "DELETE FROM [THANHVIEN] WHERE maThanhVien = ?";
            int thanhVienRows = jdbcTemplate.update(deleteThanhVienSql, maThanhVien);
            String deleteAccountSql = "DELETE FROM [TAIKHOAN] WHERE maTaiKhoan = ?";
            int accountRows = jdbcTemplate.update(deleteAccountSql, maThanhVien);

            System.out.println("Deleted THANHVIEN with maThanhVien: " + maThanhVien + ", rows affected: " + thanhVienRows);
            System.out.println("Deleted TAIKHOAN with maTaiKhoan: " + maThanhVien + ", rows affected: " + accountRows);

            return thanhVienRows > 0;
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            System.err.println("Data integrity violation when deleting THANHVIEN with maThanhVien: " + maThanhVien + ", " + e.getMessage());
            throw e; 
        } catch (Exception e) {
            System.err.println("Error deleting THANHVien with maThanhVien: " + maThanhVien + ", " + e.getMessage());
            throw new RuntimeException("Đã xảy ra lỗi không mong muốn khi xóa thành viên. Vui lòng thử lại.", e);
        }
    }

    @Override
    public List<Volunteer> searchByName(String keyword, int page, int pageSize) {
        String sql = "SELECT * FROM [THANHVIEN] WHERE hoTen LIKE ? ORDER BY maThanhVien OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = (page - 1) * pageSize;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Volunteer.class), "%" + keyword + "%", offset, pageSize);
    }

    public int countByName(String keyword) {
        String sql = "SELECT COUNT(*) FROM [THANHVIEN] WHERE hoTen LIKE ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, "%" + keyword + "%");
    }
    
    @Override
    public String getAvatar(int id) {
        String sql = "SELECT urlAvatar FROM [THANHVIEN] WHERE maThanhVien = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM [THANHVIEN] WHERE maThanhVien = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }
}
