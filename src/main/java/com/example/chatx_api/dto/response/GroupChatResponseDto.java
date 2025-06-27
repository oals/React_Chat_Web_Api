package com.example.chatx_api.dto.response;

import com.example.chatx_api.dto.GroupChatRoom;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatResponseDto {

   private List<GroupChatRoom> groupChatRoomList;

   private int totalCount;

   private Long groupChatRoomId;

   private String chatMessage;

   private Long memberId;

   private boolean isJoinNotice;

   private boolean isExitNotice;
}
