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
@WebServlet("/images/uploads/*")
public class ImageServlet extends HttpServlet {
    private static final String BASE_DIR = "D:/uploads";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String requestedFile = request.getPathInfo(); // /filename.jpg

        if (requestedFile == null || requestedFile.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        File file = new File(BASE_DIR, requestedFile);
        if (!file.exists() || file.isDirectory()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mime = request.getServletContext().getMimeType(file.getName());
        response.setContentType(mime != null ? mime : "application/octet-stream");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
