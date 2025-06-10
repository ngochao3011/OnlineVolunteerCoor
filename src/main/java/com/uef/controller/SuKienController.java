/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller;

import com.uef.model.SuKien;
import com.uef.service.SuKienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/activity")
public class SuKienController {

    @Autowired
    private SuKienService suKienService;

    @GetMapping("")
    public String listSuKien(Model model) {
        List<SuKien> danhSach = suKienService.layDanhSachSuKien();
        model.addAttribute("danhSachSuKien", danhSach);
        return "activitylist";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("suKien", new SuKien());
        return "activity/add";
    }

    @PostMapping("/add")
    public String addSuKien(@ModelAttribute SuKien suKien) {
        suKien.setThoiGianBatDau(LocalDateTime.now()); // Default start time
        suKienService.themSuKien(suKien);
        return "redirect:/activity";
    }

    @GetMapping("/edit/{maSuKien}")
    public String showEditForm(@PathVariable int maSuKien, Model model) {
        SuKien suKien = suKienService.laySuKienTheoMa(maSuKien);
        model.addAttribute("suKien", suKien);
        return "activity/edit";
    }

    @PostMapping("/edit")
    public String updateSuKien(@ModelAttribute SuKien suKien) {
        suKienService.capNhatSuKien(suKien);
        return "redirect:/activity";
    }

    @GetMapping("/delete/{maSuKien}")
    public String deleteSuKien(@PathVariable int maSuKien) {
        suKienService.xoaSuKien(maSuKien);
        return "redirect:/activity";
    }

    @PostMapping("/update")
    public String updateStatus(@RequestParam int maSuKien, @RequestParam String trangThai) {
        SuKien suKien = suKienService.laySuKienTheoMa(maSuKien);
        suKien.setTrangThai(trangThai);
        suKienService.capNhatSuKien(suKien);
        return "redirect:/activity";
    }
}
