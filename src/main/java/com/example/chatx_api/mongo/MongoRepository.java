package com.example.chatx_api.mongo;

import java.util.Optional;

public interface MongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<ChatArchive, String> {
    Optional<ChatArchive> findByChatArchiveId(Long chatArchiveId);

    void deleteByChatArchiveId(Long chatArchiveId);
}
