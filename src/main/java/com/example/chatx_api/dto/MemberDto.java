package com.example.chatx_api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long memberId;

    private String memberEmail;

    private LocalDateTime memberCreateDate;
}
