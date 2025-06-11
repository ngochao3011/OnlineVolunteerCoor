/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

/**
 *
 * @author ADMIN
 */
public interface TaiKhoanDAO {
    TaiKhoan findByEmail(String email);
    int save(TaiKhoan tk);
    boolean isEmailExists(String email);
}
