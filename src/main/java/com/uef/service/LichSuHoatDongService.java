/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

import com.uef.model.LichSuHoatDong;
import com.uef.repository.LichSuHoatDongRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LichSuHoatDongService {
    
    @Autowired
    private LichSuHoatDongRepository lichSuHoatDongRepository;

    public List<LichSuHoatDong> layTatCaLichSu() {
        return lichSuHoatDongRepository.findAll();
    }

    // Giữ phương thức cũ nếu cần
    public List<LichSuHoatDong> layLichSuTheoMaHoatDong(int maHoatDong) {
        return lichSuHoatDongRepository.findByMaHoatDong(maHoatDong);
    }
}