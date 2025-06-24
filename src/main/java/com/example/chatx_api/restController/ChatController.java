package com.example.chatx_api.restController;

import com.example.auth_common.resolver.AuthenticatedMemberId;
import com.example.chatx_api.dto.ChatArchiveDto;
import com.example.chatx_api.dto.ChatArchiveResponseDto;
import com.example.chatx_api.dto.ChatMessageDto;
import com.example.chatx_api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/api/chat/send")
    public ResponseEntity<String> chatSend(@AuthenticatedMemberId String memberId, @RequestBody ChatMessageDto chatMessageDto) {

        chatMessageDto.setMemberId(Long.parseLong(memberId));
        boolean result = chatService.chatSend(chatMessageDto);

        if (result) {
            return ResponseEntity.ok("채팅 메시지 전송 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("채팅 메시지 전송 실패");
        }
    }

    @PostMapping("/api/chat/save")
    public ResponseEntity<String> chatArchiveSave(@AuthenticatedMemberId String memberId, @RequestBody ChatArchiveDto chatArchiveDto) {

        chatArchiveDto.setMemberId(Long.parseLong(memberId));
        boolean result = chatService.chatArchiveSave(chatArchiveDto);

        if (result) {
            return ResponseEntity.ok("채팅 저장 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("채팅 저장 실패");
        }

    }

    @GetMapping("/api/chat/getChatArchive")
    public ResponseEntity<?> getChatArchive(@AuthenticatedMemberId String memberId, ChatArchiveDto chatArchiveDto) {

        Long parsedMemberId = Long.parseLong(memberId);
        chatArchiveDto.setMemberId(parsedMemberId);

        ChatArchiveResponseDto result = chatService.getChatArchive(chatArchiveDto);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("채팅 조회 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/api/chat/setChatArchiveBookmarks")
    public ResponseEntity<?> setChatArchiveBookmarks(@RequestBody ChatArchiveDto chatArchiveDto) {

        boolean result = chatService.setChatArchiveBookmarks(chatArchiveDto);

        if (result) {
            return ResponseEntity.ok("즐겨찾기 업데이트 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("즐겨찾기 업데이트 실패");
        }
    }

    @PostMapping("/api/chat/delChatArchive")
    public ResponseEntity<?> delChatArchive(@RequestBody ChatArchiveDto chatArchiveDto) {

        boolean result = chatService.delChatArchive(chatArchiveDto);

        if (result) {
            return ResponseEntity.ok("채팅 삭제 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("채팅 삭제 실패");
        }
    }

    @GetMapping("/api/chat/getChatArchiveMessage")
    public ResponseEntity<?> getChatArchiveMessage(ChatArchiveDto chatArchiveDto ) {

        String chatArchiveMessageJson = chatService.getChatArchiveMessage(chatArchiveDto);

        if (chatArchiveMessageJson != null) {
            return ResponseEntity.ok("채팅 아카이브 조회 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("채팅 아카이브 조회 실패");
        }
    }

}
