package com.example.chatx_api.mongo;

import com.example.chatx_api.dto.request.ChatArchiveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MongoController {

    private final MongoService mongoService;

    @GetMapping("/api/chat/getChatArchiveMessage")
    public ResponseEntity<?> getChatArchiveMessage(ChatArchiveRequestDto chatArchiveRequestDto) {

        Optional<ChatArchive> chatArchiveOpt = mongoService.getChatArchiveMessage(chatArchiveRequestDto);

        if (chatArchiveOpt.isPresent()) {
            ChatArchive archive = chatArchiveOpt.get();
            return ResponseEntity.ok()
                    .body(Map.of(
                            "message", "채팅 아카이브 조회 성공",
                            "data", archive
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "채팅 아카이브를 찾을 수 없습니다."));
        }

    }
}
