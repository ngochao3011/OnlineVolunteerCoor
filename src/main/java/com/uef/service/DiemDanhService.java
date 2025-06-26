/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

import com.uef.repository.DiemDanhRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class DiemDanhService {
    @Autowired
    private DiemDanhRepo diemDanhRepo;
    
    public boolean checkDiemDanh(int maThanhVien, int maHoatDong) {
        return diemDanhRepo.checkDiemDanh(maThanhVien, maHoatDong);
    }
    
    public boolean checkin(int maThanhVien, int maHoatDong) {
        return diemDanhRepo.checkin(maThanhVien, maHoatDong);
    }
}
