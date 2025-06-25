package com.uef.service;

import com.uef.model.HoatDong;
import com.uef.model.LichSuDangKy;
import com.uef.repository.DangKyRepo;
import com.uef.repository.VolunteerRepo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

        

@Service
public class DangKyService {

    @Autowired
    private DangKyRepo dangKyRepo;

    @Autowired
    private HoatDongService hoatDongService;

    @Autowired
    private VolunteerRepo volunteerRepo;

    @Transactional
   public void registerForEvent(int maTNV, int maHoatDong) throws Exception {
        if (!volunteerRepo.existsById(maTNV)) {
            throw new Exception("Không tìm thấy hồ sơ tình nguyện viên của bạn. Có thể đã có lỗi xảy ra khi tạo tài khoản. Vui lòng liên hệ quản trị viên.");
        }

        if (dangKyRepo.countActiveRegistrations(maTNV) >= 3) {
            throw new Exception("Bạn đã đăng ký tối đa 3 hoạt động. Vui lòng hủy bớt nếu muốn đăng ký hoạt động mới.");
        }

        HoatDong newEvent = hoatDongService.layHoatDongTheoMa(maHoatDong);

        // Ràng buộc 2: Chỉ được đăng ký hoạt động "Sắp diễn ra" (giữ nguyên)
        if (!"Sắp diễn ra".equals(newEvent.getTrangThai())) {
            throw new Exception("Chỉ có thể đăng ký các hoạt động ở trạng thái 'Sắp diễn ra'.");
        }

        // Ràng buộc 3: Kiểm tra trùng lịch (giữ nguyên)
        List<HoatDong> registeredEvents = dangKyRepo.findRegisteredEventsByTNV(maTNV);
        for (HoatDong registeredEvent : registeredEvents) {
            if (newEvent.getThoiGianBatDau().isBefore(registeredEvent.getThoiGianKetThuc())
                    && newEvent.getThoiGianKetThuc().isAfter(registeredEvent.getThoiGianBatDau())) {
                throw new Exception("Lịch của hoạt động này bị trùng với hoạt động '" + registeredEvent.getTenHoatDong() + "' bạn đã đăng ký.");
            }
        }

        // Nếu tất cả ràng buộc đều thỏa mãn, tiến hành đăng ký
        dangKyRepo.save(maTNV, maHoatDong);
    }

    @Transactional
    public void unregisterFromEvent(int maTNV, int maHoatDong) {
        try {
            dangKyRepo.delete(maTNV, maHoatDong);
        } catch (Exception e) {
            // Log lỗi nhưng không throw exception để tránh crash
            System.err.println("Lỗi khi hủy đăng ký: " + e.getMessage());
        }
    }

    public List<HoatDong> getAttendedHistory(int maTNV) {
        try {
            return dangKyRepo.findAttendedEventsByTNV(maTNV);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy lịch sử tham gia: " + e.getMessage());
            return List.of();
        }
    }

    public List<Integer> getRegisteredEventIds(int maTNV) {
        try {
            return dangKyRepo.findRegisteredEventIdsByTNV(maTNV);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách hoạt động đã đăng ký: " + e.getMessage());
            return List.of();
        }
    }

    public List<HoatDong> getRegisteredEvents(int maTNV) {
        try {

            return dangKyRepo.findRegisteredEventsByTNV(maTNV);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách hoạt động đã đăng ký: " + e.getMessage());
            return List.of();
        }
    }

    // Lấy danh sách các hoạt động đã hủy đăng ký (giả sử repo đã có hàm này, nếu chưa có sẽ bổ sung sau)
    public List<HoatDong> getUnregisteredHistory(int maTNV) {
        try {
            return dangKyRepo.findUnregisteredEventsByTNV(maTNV);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy lịch sử hủy đăng ký: " + e.getMessage());
            return List.of();
        }
    }

    public List<LichSuDangKy> getLichSuDangKy(int maTNV) {
        return dangKyRepo.getLichSuDangKy(maTNV);
    }

    public List<LichSuDangKy> getLichSuHuyDangKy(int maTNV) {
        return dangKyRepo.getLichSuHuyDangKy(maTNV);
    }

}
