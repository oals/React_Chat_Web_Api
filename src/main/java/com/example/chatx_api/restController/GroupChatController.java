package com.example.chatx_api.restController;

import com.example.auth_common.resolver.AuthenticatedMemberId;
import com.example.chatx_api.dto.response.GroupChatResponseDto;
import com.example.chatx_api.dto.request.GroupChatRequestDto;
import com.example.chatx_api.service.GroupChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupChatController {

    private final GroupChatService groupChatService;

    @PostMapping("/api/groupChat/create")
    public ResponseEntity<?> groupChatRoomCreate(@AuthenticatedMemberId String memberId, @RequestBody GroupChatRequestDto groupChatRequestDto) {

        groupChatRequestDto.setMemberId(Long.parseLong(memberId));
        boolean result = groupChatService.groupChatRoomCreate(groupChatRequestDto);

        if (result) {
            return ResponseEntity.ok("그룹 채팅 생성 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("그룹 채팅 생성 실패");
        }
    }

    @GetMapping("/api/groupChat/getGroupChatRoomList")
    public ResponseEntity<?> getGroupChatRoomList(@AuthenticatedMemberId String memberId, GroupChatRequestDto groupChatRequestDto) {

        groupChatRequestDto.setMemberId(Long.parseLong(memberId));
        GroupChatResponseDto groupChatRoomList = groupChatService.getGroupChatRoomList(groupChatRequestDto);

        if (groupChatRoomList != null) {
            return ResponseEntity.ok(groupChatRoomList);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("그룹 채팅 조회 실패");
        }

    }

    @PostMapping("/api/groupChat/join")
    public ResponseEntity<?> joinGroupChatRoom(@AuthenticatedMemberId String memberId, @RequestBody GroupChatRequestDto groupChatRequestDto) {

        groupChatRequestDto.setMemberId(Long.parseLong(memberId));
        boolean result = groupChatService.joinGroupChatRoom(groupChatRequestDto);

        if (result) {
            return ResponseEntity.ok("그룹 채팅 입장 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("그룹 채팅 입장 실패");
        }

    }

    @PostMapping("/api/groupChat/send")
    public ResponseEntity<?> groupChatSend(@AuthenticatedMemberId String memberId, @RequestBody GroupChatRequestDto groupChatRequestDto) {

        groupChatRequestDto.setMemberId(Long.parseLong(memberId));
        boolean result = groupChatService.groupChatSend(groupChatRequestDto);

        if (result) {
            return ResponseEntity.ok("그룹 채팅 전송 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("그룹 채팅 전송 실패");
        }

    }

    @PostMapping("/api/groupChat/joinMessage")
    public ResponseEntity<?> groupChatJoinMessage(@AuthenticatedMemberId String memberId, @RequestBody GroupChatRequestDto groupChatRequestDto) {

        groupChatRequestDto.setMemberId(Long.parseLong(memberId));
        boolean result = groupChatService.joinGroupChatMessageSend(groupChatRequestDto);

        if (result) {
            return ResponseEntity.ok("그룹 채팅 입장 메세지 전송 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("그룹 채팅 입장 메세지 전송 실패");
        }

    }

    @DeleteMapping("/api/groupChat/exit")
    public ResponseEntity<?> groupChatRoomExit(@AuthenticatedMemberId String memberId, @RequestBody GroupChatRequestDto groupChatRequestDto) {

        groupChatRequestDto.setMemberId(Long.parseLong(memberId));
        boolean result = groupChatService.groupChatRoomExit(groupChatRequestDto);

        if (result) {
            return ResponseEntity.ok("그룹 채팅 나가기 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("그룹 채팅 나가기 실패");
        }
    }

}
