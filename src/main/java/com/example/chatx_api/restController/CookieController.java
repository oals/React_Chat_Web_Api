package com.example.chatx_api.restController;

import com.example.auth_common.resolver.AuthenticatedMemberId;
import com.example.chatx_api.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CookieController {

    private final CookieService cookieService;

    @DeleteMapping("/api/cookie/clear")
    public ResponseEntity<?> clearCookie(HttpServletResponse response) {
       return cookieService.clearCookie(response);
    }

    @GetMapping("/api/cookie/getMemberId")
    public ResponseEntity<String> getMemberIdFromCookie(@AuthenticatedMemberId String memberId) {
        return ResponseEntity.ok(memberId);
    }

}
