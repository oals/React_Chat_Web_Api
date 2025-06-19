package com.example.chatx_api.service;

import com.example.chatx_api.dao.MemberDao;
import com.example.chatx_api.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{

    private final MemberDao memberDao;

    @Override
    public Long saveMember(MemberDto memberDto) {
        return memberDao.insertMember(memberDto);
    }

    @Override
    public Long getMemberId(MemberDto memberDto) {
        return memberDao.selectMember(memberDto);
    }

}
