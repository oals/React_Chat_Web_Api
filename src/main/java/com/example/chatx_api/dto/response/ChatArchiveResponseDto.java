package com.example.chatx_api.dto.response;

import com.example.chatx_api.dto.ChatArchive;
import com.example.chatx_api.dto.PaginationDto;
import com.example.chatx_api.dto.request.ChatArchiveRequestDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatArchiveResponseDto extends PaginationDto {

//    private Long chatArchiveId;
//
//    private Long memberId;
//
//    private String chatArchiveTitle;
//
//    private String chatArchiveJson;
//
//    private boolean isChatArchiveBookmarks;

//    private LocalDateTime chatArchiveDate;

    List<ChatArchive> chatArchiveDtoList;

    int totalCount;
}
