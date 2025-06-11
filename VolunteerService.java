package com.uef.service;

import com.uef.model.TaiKhoan;
import com.uef.model.TaiKhoanDAO;
import com.uef.model.Volunteer;
import com.uef.model.VolunteerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VolunteerService {

    private final VolunteerDao volunteerDao;
    private final TaiKhoanDAO taiKhoanDao;

    @Autowired
    public VolunteerService(VolunteerDao volunteerDao, TaiKhoanDAO taiKhoanDao) {
        this.volunteerDao = volunteerDao;
        this.taiKhoanDao = taiKhoanDao;
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerDao.getAll();
    }

    public Volunteer getVolunteerById(int id) {
        return volunteerDao.getById(id);
    }

    @Transactional
    public void addVolunteer(TaiKhoan taiKhoan, Volunteer volunteer) {
        System.out.println("Service Email: " + taiKhoan.getEmail());
        System.out.println("Service Mật khẩu: " + taiKhoan.getMatKhau());
        System.out.println("Service SĐT: " + taiKhoan.getSdt());
        if (taiKhoan.getMatKhau() == null || taiKhoan.getMatKhau().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống.");
        }
        int taiKhoanId = taiKhoanDao.save(taiKhoan);
        volunteer.setMaTNV(taiKhoanId);
        volunteerDao.save(volunteer);
    }

    public boolean updateVolunteer(Volunteer v) {
        return volunteerDao.update(v);
    }

    public boolean deleteVolunteer(int id) {
        return volunteerDao.delete(id);
    }

    public List<Volunteer> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return volunteerDao.getAll();  // Trả về danh sách tất cả nếu từ khóa rỗng
        }
        return volunteerDao.searchByName(keyword);
    }

    public String exportVolunteersToCSV() {
        List<Volunteer> volunteers = volunteerDao.getAll();

        StringBuilder sb = new StringBuilder();
        // Header
        sb.append("Mã Tình Nguyện Viên,Họ Tên,Số Điện Thoại,Địa Chỉ,Trạng Thái,Ngày Đăng Ký\n");

        for (Volunteer v : volunteers) {
            sb.append(v.getMaTNV()).append(",");
            sb.append(escapeCSV(v.getHoTen())).append(",");
            sb.append(escapeCSV(v.getSdtDienThoai())).append(",");
            sb.append(escapeCSV(v.getDiaChi())).append(",");
            sb.append(escapeCSV(v.getTrangThai())).append(",");
            sb.append(v.getNgayDangKy() != null ? v.getNgayDangKy().toString() : "").append("\n");
        }
        return sb.toString();
    }

    private String escapeCSV(String data) {
        if (data == null) {
            return "";
        }
        if (data.contains(",") || data.contains("\"") || data.contains("\n")) {
            data = data.replace("\"", "\"\"");
            return "\"" + data + "\"";
        }
        return data;
    }
}
