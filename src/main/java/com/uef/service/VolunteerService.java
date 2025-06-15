package com.uef.service;

import com.uef.model.LichSuThaoTac;
import com.uef.model.Volunteer;
import com.uef.repository.VolunteerDao;
import com.uef.repository.VolunteerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepo volunteerRepo;

    @Autowired
    private LichSuThaoTacService lichSuThaoTacService;

    public List<Volunteer> getAllVolunteers(int page, int pageSize) {
        return volunteerRepo.getAll(page, pageSize);
    }

    public int countAllVolunteers() {
        return ((VolunteerDao) volunteerRepo).countAll();
    }

    public Volunteer getVolunteerById(int maThanhVien) {
        return volunteerRepo.getById(maThanhVien);
    }

    public boolean addVolunteer(Volunteer v) {
        return volunteerRepo.save(v);
    }

    public boolean updateVolunteer(Volunteer v) {
        Volunteer old = volunteerRepo.getById(v.getMaThanhVien());
        if (old == null) {
            return false;
        }

        boolean success = volunteerRepo.update(v);

        if (success) {
            LichSuThaoTac log = new LichSuThaoTac();
            log.setMaThanhVien(v.getMaThanhVien());
            log.setHanhDong("Sửa");
            log.setTruocKhiSua(old.toString());
            log.setSauKhiSua(v.toString());
            log.setThoiGian(new Date());
            lichSuThaoTacService.ghiNhanLichSu(log);
        }

        return success;
    }

    public boolean deleteVolunteer(int maThanhVien) {
        Volunteer old = volunteerRepo.getById(maThanhVien);
        if (old == null) {
            return false;
        }

        boolean deleted = volunteerRepo.delete(maThanhVien);

        if (deleted) {
            LichSuThaoTac log = new LichSuThaoTac();
            log.setMaThanhVien(maThanhVien);
            log.setHanhDong("Xóa");
            log.setTruocKhiSua(old.toString());
            log.setSauKhiSua("");  // Đã xóa, không còn dữ liệu
            log.setThoiGian(new Date());
            lichSuThaoTacService.ghiNhanLichSu(log);
        }

        return deleted;
    }

    public List<Volunteer> searchByName(String keyword, int page, int pageSize) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return volunteerRepo.getAll(page, pageSize);
        }
        return volunteerRepo.searchByName(keyword.trim(), page, pageSize);
    }

    public int countVolunteersByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return countAllVolunteers();
        }
        return ((VolunteerDao) volunteerRepo).countByName(keyword.trim());
    }

    public String exportVolunteersToCSV() {
        List<Volunteer> volunteers = volunteerRepo.getAll(1, Integer.MAX_VALUE); // Lấy tất cả để export
        StringBuilder sb = new StringBuilder();
sb.append("Mã Thành Viên,Họ Tên,Số Điện Thoại,Địa Chỉ,Trạng Thái,Ngày Đăng Ký,Chức Vụ,URL Avatar\n");
        for (Volunteer v : volunteers) {
            sb.append(v.getMaThanhVien()).append(",");
            sb.append(escapeCSV(v.getHoTen())).append(",");
            sb.append(escapeCSV(v.getSdt())).append(",");
            sb.append(escapeCSV(v.getDiaChi())).append(",");
            sb.append(escapeCSV(v.getTrangThai())).append(",");
            sb.append(v.getNgayDangKy() != null ? v.getNgayDangKy().toString() : "").append(",");
            sb.append(escapeCSV(v.getChucVu())).append(",");
            sb.append(escapeCSV(v.getUrlAvatar())).append("\n");
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
    public Volunteer getById(int id) {
        return volunteerRepo.getById(id);
    }
}