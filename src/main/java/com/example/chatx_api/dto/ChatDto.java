package com.example.chatx_api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    private Long chatRoomId;

    private Long senderId;

    private String chatMessage;

}
