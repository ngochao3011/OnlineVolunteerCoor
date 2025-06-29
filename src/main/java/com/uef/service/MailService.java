/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class MailService {
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendTestMail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Test Email");
        message.setText("Đây là email test từ Spring MVC.");
        message.setFrom("haoln21@uef.edu.vn");

        mailSender.send(message);
    }
    
    public void sendApprovedEmail(String to, String hoTen, String tenHoatDong) {
        String subject = "Yêu cầu tham gia đã được duyệt";
        String body = "Xin chào " + hoTen + ",\n\n"
                    + "Yêu cầu tham gia hoạt động \"" + tenHoatDong + "\" đã được điều phối viên duyệt.\n"
                    + "Vui lòng kiểm tra trong hệ thống để biết thêm chi tiết.\n\n"
                    + "Trân trọng.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    
    public void sendPassword(String to, String newPassword) {
        String subject = "Mật khẩu mới từ Online Volunteer Coo";
        String body = "Mật khẩu mới của bạn là: " + newPassword + "\nVui lòng đăng nhập và đổi lại mật khẩu."
                    + "Trân trọng.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
