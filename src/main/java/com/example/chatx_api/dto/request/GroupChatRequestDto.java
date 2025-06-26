package com.example.chatx_api.dto.request;

import com.example.chatx_api.dto.PaginationDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatRequestDto extends PaginationDto {

    private Long groupChatRoomId;

    private Long memberId;

    private String groupChatRoomTitle;

    private String groupChatRoomMaxParticipants;

    private String groupChatRoomTopic;

    private String chatMessage;

}
