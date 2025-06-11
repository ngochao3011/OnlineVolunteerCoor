/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.repository;

import com.uef.model.Volunteer;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public interface VolunteerRepo {
    public List<Volunteer> getAll();
    public Volunteer getById(int id);
    public boolean save(Volunteer volunteer);
    public boolean update(Volunteer v);
    public boolean delete(int id);
    public List<Volunteer> searchByName(String keyword);
}
