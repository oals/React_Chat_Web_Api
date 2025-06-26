package com.example.chatx_api.mongo;

import com.example.chatx_api.dto.request.ChatArchiveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatArchiveService {

    private final ChatArchiveRepository chatArchiveRepository;

    public void insertChatArchive(ChatArchive chatArchive) {
        chatArchiveRepository.save(chatArchive);
    }

    public Optional<ChatArchive> getChatArchiveMessage(ChatArchiveRequestDto chatArchiveRequestDto) {
        return chatArchiveRepository.findByChatArchiveId(chatArchiveRequestDto.getChatArchiveId());
    }

}
