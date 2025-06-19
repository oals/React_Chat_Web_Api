package com.example.chatx_api.dao;

import com.example.chatx_api.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

    Long insertMember(MemberDto memberDto);

    Long selectMember(MemberDto memberDto);
}
