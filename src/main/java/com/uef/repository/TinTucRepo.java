/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import com.uef.model.TinTuc;
import java.util.List;

/**
 *
 * @author Asus
 */
public interface TinTucRepo {

    List<TinTuc> getTinNgauNhien();

    List<TinTuc> getTinGanDay();

    List<String> getDanhMucs();

    List<String> getTopHashtags();  // cần có
}
