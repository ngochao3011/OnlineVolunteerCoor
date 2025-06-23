/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.filter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author ADMIN
 */
@WebServlet("/images/qrcodes/*")
public class QRImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String qrFile = request.getPathInfo().substring(1); // lấy tên file
        File file = new File("D:/uploads/qrcodes/" + qrFile);

        if (file.exists()) {
            response.setContentType("image/png");
            Files.copy(file.toPath(), response.getOutputStream());
        } else {
            response.sendError(404);
        }
    }
}
