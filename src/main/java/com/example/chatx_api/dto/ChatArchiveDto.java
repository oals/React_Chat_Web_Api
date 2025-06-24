package com.example.chatx_api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatArchiveDto extends PaginationDto {

    private Long chatArchiveId;

    private Long memberId;

    private String chatArchiveTitle;

    private String chatArchiveJson;

    private boolean isChatArchiveBookmarks;

    private LocalDateTime chatArchiveDate;

}
