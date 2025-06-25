package com.uef.repository;

import com.uef.model.HoatDong;
import java.time.LocalDateTime;
import com.uef.model.LichSuDangKy;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DangKyRepo {

    void save(int maTNV, int maHoatDong);

    void delete(int maTNV, int maHoatDong);

    int countActiveRegistrations(int maTNV);

    List<HoatDong> findRegisteredEventsByTNV(int maTNV);

    List<Integer> findRegisteredEventIdsByTNV(int maTNV);

    List<HoatDong> findAttendedEventsByTNV(int maTNV);

    List<HoatDong> findUnregisteredEventsByTNV(int maTNV);

    List<LichSuDangKy> getLichSuDangKy(int maTNV);
    List<LichSuDangKy> getLichSuHuyDangKy(int maTNV);

}
