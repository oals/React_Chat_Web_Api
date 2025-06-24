package com.example.chatx_api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private Long chatRoomId;

    private Long memberId;

    private String chatMessage;
}
