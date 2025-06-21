/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;



/**
 *
 * @author Asus
 */
import com.uef.model.ThongKe;
import com.uef.repository.ThongKeRepo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ThongKeService {

    private final ThongKeRepo thongKeRepo;

    public ThongKeService(ThongKeRepo thongKeRepo) {
        this.thongKeRepo = thongKeRepo;
    }

    public Map<String, Integer> laySoLieuThongKeTheoThang() {
        return thongKeRepo.getThongKeTheoThang();
    }

    public Map<String, Integer> laySoLieuThongKeTheoTrangThai() {
        return thongKeRepo.getThongKeTheoTrangThai();
    }

    public ThongKe layThongKeTongQuan() {
        ThongKe thongKe = new ThongKe();

        // Tổng số hoạt động
        int tong = thongKeRepo.getTongSoHoatDong();
        int tongTruoc = thongKeRepo.getSoHoatDongThangTruoc();
        thongKe.setTongSoHoatDong(tong);
        thongKe.setTiLeTangTruongTong(tinhTiLe(tong, tongTruoc));

        // Hoàn thành
        int hoanThanh = thongKeRepo.getSoHoanThanh();
        int hoanThanhTruoc = thongKeRepo.getSoHoanThanhThangTruoc();
        thongKe.setSoHoanThanh(hoanThanh);
        thongKe.setTiLeTangTruongHoanThanh(tinhTiLe(hoanThanh, hoanThanhTruoc));

        // Đang thực hiện
        int dangThucHien = thongKeRepo.getSoDangThucHien();
        int dangThucHienTruoc = thongKeRepo.getSoDangThucHienThangTruoc();
        thongKe.setSoDangThucHien(dangThucHien);
        thongKe.setTiLeTangTruongDangThucHien(tinhTiLe(dangThucHien, dangThucHienTruoc));

        // Đã hủy
        int daHuy = thongKeRepo.getSoDaHuy();
        int daHuyTruoc = thongKeRepo.getSoDaHuyThangTruoc();
        thongKe.setSoDaHuy(daHuy);
        thongKe.setTiLeTangTruongDaHuy(tinhTiLe(daHuy, daHuyTruoc));

        return thongKe;
    }

    private double tinhTiLe(int hienTai, int truocDo) {
        if (truocDo == 0) {
            return hienTai > 0 ? 100.0 : 0.0;
        }
        return ((double) (hienTai - truocDo) / truocDo) * 100;
    }
}
