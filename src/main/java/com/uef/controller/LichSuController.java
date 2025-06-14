/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.service.LichSuThaoTacService;
import com.uef.model.LichSuThaoTac;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ACER
 */
@Controller
@RequestMapping("/lichsu")
public class LichSuController {

    @Autowired
    private LichSuThaoTacService lichSuService;

    @GetMapping
    public String hienThiLichSu(Model model) {
        List<LichSuThaoTac> list = lichSuService.getAll();
        model.addAttribute("lichSuList", list);
        return "LichSu"; // đây là lichsuthao.jsp
    }
}

