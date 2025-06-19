package com.example.chatx_api.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface FireBaseService {

    ResponseEntity<Void> verifyFbToken(HttpServletResponse response,String idToken);

}
