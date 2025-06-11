package com.uef.service;

import com.uef.repository.TaiKhoanDAO;
import com.uef.model.Volunteer;
import com.uef.model.VolunteerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {

    private final VolunteerDao volunteerDao;

    @Autowired
    public VolunteerService(VolunteerDao volunteerDao, TaiKhoanDAO taiKhoanDao) {
        this.volunteerDao = volunteerDao;
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerDao.getAll();
    }

    public Volunteer getVolunteerById(int id) {
        return volunteerDao.getById(id);
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
