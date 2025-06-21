package com.example.chatx_api.restController;

import com.example.chatx_api.service.FireBaseService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class FireBaseController {

    private final FireBaseService fireBaseService;

    @PostMapping("/api/auth/fireBase")
    public ResponseEntity<Void> fireBaseAuthing(HttpServletResponse response, @RequestHeader("Authorization") String token) {

        String idToken = token.replace("Bearer ", "");
        return fireBaseService.verifyFbToken(response,idToken);
    }

}
