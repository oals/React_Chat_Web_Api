package com.example.chatx_api.service;

import com.example.chatx_api.dto.ChatArchiveDto;
import com.example.chatx_api.dto.ChatArchiveResponseDto;
import com.example.chatx_api.dto.ChatMessageDto;

import java.util.List;

public interface ChatService {

    boolean chatSend(ChatMessageDto chatMessageDto);

    boolean chatArchiveSave(ChatArchiveDto chatArchiveDto);

    ChatArchiveResponseDto getChatArchive(ChatArchiveDto chatArchiveDto);

    String getChatArchiveMessage(ChatArchiveDto chatArchiveDto);

    boolean setChatArchiveBookmarks(ChatArchiveDto chatArchiveDto);

    boolean delChatArchive(ChatArchiveDto chatArchiveDto);
}
