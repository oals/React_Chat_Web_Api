package com.example.chatx_api.dao;

import com.example.chatx_api.dto.ChatArchiveDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDao {

    void insertChatArchive(ChatArchiveDto chatArchiveDto);

    List<ChatArchiveDto> selectChatArchive(ChatArchiveDto chatArchiveDto);

    int selectChatArchiveTotalCount(ChatArchiveDto chatArchiveDto);

    void updateChatArchiveBookmarks(ChatArchiveDto chatArchiveDto);

    void deleteChatArchive(ChatArchiveDto chatArchiveDto);

}
