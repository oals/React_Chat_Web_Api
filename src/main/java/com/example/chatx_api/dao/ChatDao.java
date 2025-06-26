package com.example.chatx_api.dao;

import com.example.chatx_api.dto.ChatArchive;
import com.example.chatx_api.dto.GroupChatRoom;
import com.example.chatx_api.dto.request.ChatArchiveRequestDto;
import com.example.chatx_api.dto.response.GroupChatResponseDto;
import com.example.chatx_api.dto.response.ChatArchiveResponseDto;
import com.example.chatx_api.dto.request.GroupChatRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDao {

    void insertChatArchive(ChatArchiveRequestDto chatArchiveRequestDto);

    List<ChatArchive> selectChatArchive(ChatArchiveRequestDto chatArchiveRequestDto);

    int selectChatArchiveTotalCount(ChatArchiveRequestDto chatArchiveRequestDto);

    void updateChatArchiveBookmarks(ChatArchiveRequestDto chatArchiveRequestDto);

    void deleteChatArchive(ChatArchiveRequestDto chatArchiveRequestDto);


    void insertGroupChatRoom(GroupChatRequestDto groupChatRequestDto);

    void insertGroupChatRoomMember(GroupChatRequestDto groupChatRequestDto);

    List<GroupChatRoom> selectGroupChatRoom(GroupChatRequestDto groupChatRequestDto);

    int selectGroupChatRoomTotalCount(GroupChatRequestDto groupChatRequestDto);

}
