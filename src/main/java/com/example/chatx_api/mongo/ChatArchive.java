package com.example.chatx_api.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chatArchiveMessage")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatArchive {

    @Id
    private Long chatArchiveId;

    private String chatArchiveJson;

    private LocalDateTime chatArchiveCreateDate;

}
