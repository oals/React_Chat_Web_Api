package com.example.chatx_api.mongo;

import com.example.chatx_api.dto.request.ChatArchiveRequestDto;

import java.util.Optional;

public interface MongoService {

    void insertChatArchive(ChatArchive chatArchive);

    void deleteChatArchive(ChatArchiveRequestDto chatArchiveRequestDto);

    Optional<ChatArchive> getChatArchiveMessage(ChatArchiveRequestDto chatArchiveRequestDto);

}
