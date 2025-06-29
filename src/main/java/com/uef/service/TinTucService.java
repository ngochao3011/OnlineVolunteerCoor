/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

import com.uef.model.TinTuc;
import com.uef.repository.TinTucRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class TinTucService {
    
    @Autowired
    private TinTucRepo tinTucRepo;

    
    public List<TinTuc> getTinNgauNhien() {
        return tinTucRepo.getTinNgauNhien();
    }

   
    public List<TinTuc> getTinGanDay() {
        return tinTucRepo.getTinGanDay();
    }

   
    public List<String> getDanhMucs() {
        return tinTucRepo.getDanhMucs();
    }

   
    public List<String> getTopHashtags() {
        return tinTucRepo.getTopHashtags();
    }
    
}
