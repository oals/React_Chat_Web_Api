package com.example.chatx_api.mongo;

import com.example.chatx_api.dto.request.ChatArchiveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoServiceImpl implements MongoService {

    private final MongoRepository mongoRepository;

    public void insertChatArchive(ChatArchive chatArchive) {
        mongoRepository.save(chatArchive);
    }

    @Override
    public void deleteChatArchive(ChatArchiveRequestDto chatArchiveRequestDto) {
        mongoRepository.deleteByChatArchiveId(chatArchiveRequestDto.getChatArchiveId());
    }

    public Optional<ChatArchive> getChatArchiveMessage(ChatArchiveRequestDto chatArchiveRequestDto) {
        return mongoRepository.findByChatArchiveId(chatArchiveRequestDto.getChatArchiveId());
    }

}
