package com.example.chatx_api.restController;

import com.example.auth_common.resolver.AuthenticatedMemberId;
import com.example.chatx_api.dto.ChatDto;
import com.example.chatx_api.service.ChatService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/api/chat/send")
    public ResponseEntity<String> chatSend(@AuthenticatedMemberId String memberId, @RequestBody ChatDto chatDto) {

        chatDto.setSenderId(Long.parseLong(memberId));
        boolean result = chatService.chatSend(chatDto);

        if (result) {
            return ResponseEntity.ok("채팅 메시지 전송 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("채팅 메시지 전송 실패");
        }
    }
}
