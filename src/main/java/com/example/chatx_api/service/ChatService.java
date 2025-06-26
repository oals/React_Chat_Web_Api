package com.example.chatx_api.service;

import com.example.chatx_api.dto.request.ChatArchiveRequestDto;
import com.example.chatx_api.dto.response.ChatArchiveResponseDto;
import com.example.chatx_api.dto.request.ChatRequestDto;

public interface ChatService {

    boolean chatSend(ChatRequestDto chatRequestDto);

    boolean chatArchiveSave(ChatArchiveRequestDto chatArchiveRequestDto);

    ChatArchiveResponseDto getChatArchive(ChatArchiveRequestDto chatArchiveRequestDto);

    boolean setChatArchiveBookmarks(ChatArchiveRequestDto chatArchiveRequestDto);

    boolean delChatArchive(ChatArchiveRequestDto chatArchiveRequestDto);
}
