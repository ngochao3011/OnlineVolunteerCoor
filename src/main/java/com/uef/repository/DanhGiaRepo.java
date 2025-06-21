package com.uef.repository;

import com.uef.model.DanhGia;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhGiaRepo {

    void save(DanhGia danhGia);

    boolean hasUserReviewedEvent(int maTNV, int maHoatDong);

    boolean didUserAttendEvent(int maTNV, int maHoatDong);

    List<Integer> getReviewedEventIdsByUser(int maTNV);

}
