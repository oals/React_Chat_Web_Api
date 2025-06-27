package com.example.chatx_api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatRoom {

    private Long groupChatRoomId;

    private Long memberId;

    private String groupChatRoomTitle;
    
    private int currentParticipants;

    private String groupChatRoomTopic;

    private LocalDateTime groupChatRoomCreateDate;

}
