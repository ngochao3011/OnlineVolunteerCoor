/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public interface DiemDanhRepo {
    boolean checkDiemDanh(int maThanhVien, int maHoatDong);
    boolean checkin(int maThanhVien, int maHoatDong);
}
