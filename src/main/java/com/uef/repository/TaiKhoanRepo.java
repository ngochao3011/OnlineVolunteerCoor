/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import com.uef.model.TaiKhoan;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public interface TaiKhoanRepo {
    TaiKhoan findByEmail(String email);
    int save(TaiKhoan tk);
    boolean isEmailExists(String email);
}
