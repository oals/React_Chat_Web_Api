package com.example.chatx_api.restController;

import com.example.auth_common.resolver.AuthenticatedMemberId;
import com.example.chatx_api.dto.request.ChatArchiveRequestDto;
import com.example.chatx_api.dto.response.ChatArchiveResponseDto;
import com.example.chatx_api.dto.request.ChatRequestDto;
import com.example.chatx_api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/api/chat/send")
    public ResponseEntity<String> chatSend(@AuthenticatedMemberId String memberId, @RequestBody ChatRequestDto chatRequestDto) {

        chatRequestDto.setMemberId(Long.parseLong(memberId));
        boolean result = chatService.chatSend(chatRequestDto);

        if (result) {
            return ResponseEntity.ok("채팅 메시지 전송 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("채팅 메시지 전송 실패");
        }
    }

    @PostMapping("/api/chat/save")
    public ResponseEntity<String> chatArchiveSave(@AuthenticatedMemberId String memberId, @RequestBody ChatArchiveRequestDto chatArchiveRequestDto) {

        chatArchiveRequestDto.setMemberId(Long.parseLong(memberId));
        boolean result = chatService.chatArchiveSave(chatArchiveRequestDto);

        if (result) {
            return ResponseEntity.ok("채팅 저장 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("채팅 저장 실패");
        }

    }

    @GetMapping("/api/chat/getChatArchive")
    public ResponseEntity<?> getChatArchive(@AuthenticatedMemberId String memberId, ChatArchiveRequestDto chatArchiveRequestDto) {

        Long parsedMemberId = Long.parseLong(memberId);
        chatArchiveRequestDto.setMemberId(parsedMemberId);

        ChatArchiveResponseDto result = chatService.getChatArchive(chatArchiveRequestDto);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("채팅 조회 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/api/chat/setChatArchiveBookmarks")
    public ResponseEntity<?> setChatArchiveBookmarks(@RequestBody ChatArchiveRequestDto chatArchiveRequestDto) {

        boolean result = chatService.setChatArchiveBookmarks(chatArchiveRequestDto);

        if (result) {
            return ResponseEntity.ok("즐겨찾기 업데이트 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("즐겨찾기 업데이트 실패");
        }
    }

    @PostMapping("/api/chat/delChatArchive")
    public ResponseEntity<?> delChatArchive(@RequestBody ChatArchiveRequestDto chatArchiveRequestDto) {

        boolean result = chatService.delChatArchive(chatArchiveRequestDto);

        if (result) {
            return ResponseEntity.ok("채팅 삭제 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("채팅 삭제 실패");
        }
    }

}
