package com.example.chatx_api.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface CookieService {

    void setCookie(String memberId, HttpServletResponse response);

    ResponseEntity<?> clearCookie(HttpServletResponse response);
}
