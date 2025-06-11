package com.uef.service;

import com.uef.model.Volunteer;
import com.uef.repository.VolunteerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepo volunteerRepo;

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepo.getAll();
    }

    public Volunteer getVolunteerById(int id) {
        return volunteerRepo.getById(id);
    }
    
    public boolean addVolunteer(Volunteer v) {
        return volunteerRepo.save(v); // Đăng ký thông tin tình nguyện viên
    }
    
    public boolean updateVolunteer(Volunteer v) {
        return volunteerRepo.update(v);
    }

    public boolean deleteVolunteer(int id) {
        return volunteerRepo.delete(id);
    }

    public List<Volunteer> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return volunteerRepo.getAll();  // Trả về danh sách tất cả nếu từ khóa rỗng
        }
        return volunteerRepo.searchByName(keyword);
    }

    public String exportVolunteersToCSV() {
        List<Volunteer> volunteers = volunteerRepo.getAll();

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
