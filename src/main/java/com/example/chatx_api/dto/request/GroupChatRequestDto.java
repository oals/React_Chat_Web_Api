package com.example.chatx_api.dto.request;

import com.example.chatx_api.dto.PaginationDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatRequestDto extends PaginationDto {

    private Long groupChatRoomId;

    private Long memberId;

    private String groupChatRoomTitle;

    private String groupChatRoomTopic;

    private String chatMessage;

    private String searchText;

    private List<Long> groupChatRoomIdList;

}
