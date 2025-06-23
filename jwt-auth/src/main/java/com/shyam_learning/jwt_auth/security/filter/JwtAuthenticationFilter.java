package com.shyam_learning.jwt_auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyam_learning.jwt_auth.security.JwtUtil;
import com.shyam_learning.jwt_auth.security.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * It can be renamed to JwtTokenCreationFilter
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!request.getServletPath().equals("/generate-token")) {
            filterChain.doFilter(request, response);
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authResult = authenticationManager.authenticate(authenticationToken);

        if (authResult.isAuthenticated()) {
            String JwtToken = JwtUtil.generateJwtToken(loginRequest.getUsername(), 2);
            response.addHeader("Authorization", "Bearer " + JwtToken);

            String refreshToken = JwtUtil.generateJwtToken(loginRequest.getUsername(), 7 * 24 * 60); // 7days

            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7days
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/refresh-token");
            response.addCookie(cookie);
        }
    }
}
