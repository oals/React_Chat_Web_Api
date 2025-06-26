package com.example.chatx_api.dto;

import com.example.chatx_api.dto.request.ChatArchiveRequestDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatArchive {

    private Long chatArchiveId;

    private String chatArchiveTitle;

    private String chatArchiveJson;

    private boolean isChatArchiveBookmarks;

    private LocalDateTime chatArchiveDate;

    private Long memberId;

}
