package com.example.chatx_api.service;

import com.example.chatx_api.dto.response.GroupChatResponseDto;
import com.example.chatx_api.dto.request.GroupChatRequestDto;

import java.util.List;

public interface GroupChatService {

    boolean groupChatSend(GroupChatRequestDto groupChatRequestDto);

    boolean groupChatRoomCreate(GroupChatRequestDto groupChatRequestDto);

    GroupChatResponseDto getGroupChatRoomList(GroupChatRequestDto groupChatRequestDto);

    boolean joinGroupChatRoom(GroupChatRequestDto groupChatRequestDto);

    boolean joinGroupChatMessageSend(GroupChatRequestDto groupChatRequestDto);

    boolean groupChatRoomExit(GroupChatRequestDto groupChatRequestDto);
}
