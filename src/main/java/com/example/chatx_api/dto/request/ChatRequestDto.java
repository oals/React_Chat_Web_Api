package com.example.chatx_api.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestDto {

    private Long memberId;

    private Long chatRoomId;

    private String chatMessage;

}
