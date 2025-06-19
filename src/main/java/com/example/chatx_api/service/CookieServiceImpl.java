package com.example.chatx_api.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CookieServiceImpl implements CookieService{
    @Override
    public void setCookie(String memberId, HttpServletResponse response) {

        String cookieValue = String.format("memberId=%s; Max-Age=%d; Path=/; HttpOnly; SameSite=Strict%s",
                memberId,
                24 * 60 * 60,
                "");

        response.setHeader("Set-Cookie", cookieValue);
    }

    @Override
    public ResponseEntity<?> clearCookie(HttpServletResponse response) {

        Cookie cookie = new Cookie("memberId", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return ResponseEntity.ok("로그아웃 및 쿠키 제거 성공");
    }
}
