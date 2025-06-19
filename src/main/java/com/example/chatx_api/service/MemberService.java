package com.example.chatx_api.service;

import com.example.chatx_api.dto.MemberDto;

public interface MemberService {

    Long saveMember(MemberDto memberDto);

    Long getMemberId(MemberDto memberDto);
}
