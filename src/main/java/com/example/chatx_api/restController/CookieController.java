package com.example.chatx_api.restController;

import com.example.chatx_api.service.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CookieController {

    private final CookieService cookieService;

    @PostMapping("/api/clear-cookie")
    public ResponseEntity<?> clearCookie(HttpServletResponse response) {
       return cookieService.clearCookie(response);
    }




}
