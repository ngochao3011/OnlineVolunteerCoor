package com.uef.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class TaiKhoanDAOImpl implements TaiKhoanDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Inject BCryptPasswordEncoder

    @Override
    public TaiKhoan findByEmail(String email) {
        String sql = "SELECT * FROM [Tài Khoản] WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TaiKhoan.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int save(TaiKhoan tk) {
        String sql = "INSERT INTO [Tài Khoản] (email, matKhau, hoTen, sdtDienThoai, quyenHan) VALUES (?, ?, ?, ?, ?)";
        // Mã hóa mật khẩu bằng BCryptPasswordEncoder
        String hashedPassword = passwordEncoder.encode(tk.getMatKhau());
        // Thực hiện insert
        jdbcTemplate.update(sql, tk.getEmail(), hashedPassword, tk.getHoTen(), tk.getSdt(), tk.getQuyenHan());
        // Lấy ID vừa chèn
        return jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Integer.class);
    }

    @Override
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM [Tài Khoản] WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

}
