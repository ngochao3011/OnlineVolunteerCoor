/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.filter;

import com.uef.model.TaiKhoan;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author ADMIN
 */

@WebFilter(urlPatterns = {"/volunteer", "/volunteer/*", "/activity", "/activity/*"})
public class AuthorizationFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        TaiKhoan user = (session != null) ? (TaiKhoan) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/sign-in");
            return;
        }

        // Chỉ cho phép Điều phối viên truy cập đường dẫn volunteer, activity
        if (!"Điều phối viên".equals(user.getQuyenHan())) {
            response.sendRedirect(request.getContextPath() + "/403");
            return;
        }

        chain.doFilter(req, res);
    }
}
