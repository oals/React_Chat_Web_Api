package com.example.chatx_api.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

@Service
public class CookieServiceImpl implements CookieService{

    @Value("${auth.secret-key}")
    private String SECRET_KEY;

    @Override
    public void setCookie(String memberId, HttpServletResponse response) {

        String secretKey = SECRET_KEY;

        String signature = hmacSha256(memberId, secretKey);
        String cookieValue = memberId + "|" + signature;

        ResponseCookie cookie = ResponseCookie.from("memberId", cookieValue)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(1))
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

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

    private String hmacSha256(String value, String key) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmac.init(keySpec);
            byte[] hash = hmac.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("HMAC 생성 실패", e);
        }
    }

}
