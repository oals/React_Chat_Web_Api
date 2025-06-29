package com.example.chatx_api.redis;

import com.example.auth_common.resolver.AuthenticatedMemberId;
import com.example.chatx_api.dto.request.GroupChatRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/api/groupChat/join")
    public ResponseEntity<?> joinGroupChatRoom(@AuthenticatedMemberId String memberId, @RequestBody GroupChatRequestDto groupChatRequestDto) {

        groupChatRequestDto.setMemberId(Long.parseLong(memberId));
        boolean result = redisService.joinGroupChatRoom(groupChatRequestDto);

        if (result) {
            return ResponseEntity.ok("그룹 채팅 입장 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("그룹 채팅 입장 실패");
        }

    }

    @DeleteMapping("/api/groupChat/exit")
    public ResponseEntity<?> groupChatRoomExit(@AuthenticatedMemberId String memberId, @RequestBody GroupChatRequestDto groupChatRequestDto) {

        groupChatRequestDto.setMemberId(Long.parseLong(memberId));
        boolean result = redisService.groupChatRoomExit(groupChatRequestDto);

        if (result) {
            return ResponseEntity.ok("그룹 채팅 나가기 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("그룹 채팅 나가기 실패");
        }
    }

    @GetMapping("/api/groupChat/memberCount")
    public ResponseEntity<?> groupChatRoomMemberCount(@AuthenticatedMemberId String memberId, GroupChatRequestDto groupChatRequestDto) {

        groupChatRequestDto.setMemberId(Long.parseLong(memberId));
        Long memberCount = redisService.getGroupChatRoomMemberCount(groupChatRequestDto);

        if (memberCount != null) {
            return ResponseEntity.ok(memberCount);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("그룹 채팅 멤버 카운트 조회 실패");
        }
    }

}
