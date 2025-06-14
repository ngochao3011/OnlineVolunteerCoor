/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

import com.uef.model.TaiKhoan;
import com.uef.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class TaiKhoanService {
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;
        
    public boolean dangKyTaiKhoan(TaiKhoan tkNew) {
        if (getEmail(tkNew.getEmail()) != null) {
            return false; // Đã tồn tại tài khoản
        }
        taiKhoanRepo.save(tkNew); // Lưu tài khoản mới
        return true;
    }
    
    public TaiKhoan getEmail(String email){
        return taiKhoanRepo.findByEmail(email);
    }
    
    public Integer getID(String email){
        return taiKhoanRepo.getID(email);
    }
}
