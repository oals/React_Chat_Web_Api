package com.example.chatx_api.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatArchiveRepository extends MongoRepository<ChatArchive, String> {
    Optional<ChatArchive> findByChatArchiveId(Long chatArchiveId);
}
