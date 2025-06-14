/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import com.uef.model.LichSuThaoTac;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface LichSuRepo {

    boolean save(LichSuThaoTac log);
    List<LichSuThaoTac> getAll();

}
