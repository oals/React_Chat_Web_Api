package com.example.chatx_api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatArchiveResponseDto {

    List<ChatArchiveDto> chatArchiveDtoList;

    int totalCount;
}
