package com.uef.service;

import com.uef.model.DanhGia;
import com.uef.repository.DanhGiaRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DanhGiaService {

    @Autowired
    private DanhGiaRepo danhGiaRepo;

    public void submitReview(DanhGia danhGia) throws Exception {
        // Ràng buộc 1: Kiểm tra xem đã đánh giá hoạt động này chưa
        if (danhGiaRepo.hasUserReviewedEvent(danhGia.getMaTNV(), danhGia.getMaHoatDong())) {
            throw new Exception("Bạn đã đánh giá hoạt động này rồi.");
        }

        // Ràng buộc 2: Kiểm tra xem có tham gia và được điểm danh "Có mặt" không
        if (!danhGiaRepo.didUserAttendEvent(danhGia.getMaTNV(), danhGia.getMaHoatDong())) {
            throw new Exception("Bạn chỉ có thể đánh giá các hoạt động đã được ghi nhận 'Có mặt'.");
        }

        danhGiaRepo.save(danhGia);
    }

    // Các phương thức cần thiết để kiểm tra ở tầng Controller
    public boolean hasReviewed(int maTNV, int maHoatDong) {
        return danhGiaRepo.hasUserReviewedEvent(maTNV, maHoatDong);
    }

    public boolean canReview(int maTNV, int maHoatDong) {
        return danhGiaRepo.didUserAttendEvent(maTNV, maHoatDong);
    }

    public List<Integer> getReviewedEventIds(int maTNV) {
        return danhGiaRepo.getReviewedEventIdsByUser(maTNV);
    }
}
