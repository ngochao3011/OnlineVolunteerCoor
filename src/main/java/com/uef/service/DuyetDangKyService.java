/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

import com.uef.model.DuyetDangKy;
import com.uef.repository.DuyetDangKyRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DuyetDangKyService {

    @Autowired
    private DuyetDangKyRepository duyetDangKyRepository;

    public List<DuyetDangKy> getDanhSachDangKyCanDuyet(int page, String keyword, String location, String trangThai) {
        return duyetDangKyRepository.layDanhSachDangKyCanDuyet(page, keyword, location, trangThai);
    }

    public int getTongSoDangKyCanDuyet(String keyword, String location, String trangThai) {
        return duyetDangKyRepository.demTongSoDangKyCanDuyet(keyword, location, trangThai);
    }

    public DuyetDangKy getDangKyCanDuyetTheoMa(int maDDK) {
        return duyetDangKyRepository.layDangKyCanDuyetTheoMa(maDDK);
    }

    public void capNhatTrangThaiDuyet(int maDDK, String trangThaiDuyet, String ghiChu) {
        duyetDangKyRepository.capNhatTrangThaiDuyet(maDDK, trangThaiDuyet, ghiChu);
    }
    
    public boolean checkDangKy(int maThanhVien, int maHoatDong){
        return duyetDangKyRepository.checkDangKy(maThanhVien, maHoatDong);
    }
    
    public Map<String, Object> getThongTinMail(int maDDK){
        return duyetDangKyRepository.getThongTinMail(maDDK);
    }
}
