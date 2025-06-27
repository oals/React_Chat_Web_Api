package com.example.chatx_api.service;

import com.example.chatx_api.dao.ChatDao;
import com.example.chatx_api.dto.GroupChatRoom;
import com.example.chatx_api.dto.response.GroupChatResponseDto;
import com.example.chatx_api.dto.request.GroupChatRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupChatServiceImpl implements GroupChatService{

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatDao chatDao;


    private void stompMessageSend(GroupChatResponseDto groupChatResponseDto) {
        System.out.println("전송 경로 : /topic/groupChat/" + groupChatResponseDto.getGroupChatRoomId());
        String destination = "/topic/groupChat/" + groupChatResponseDto.getGroupChatRoomId();
        messagingTemplate.convertAndSend(destination, groupChatResponseDto);
    }


    @Override
    public boolean groupChatSend(GroupChatRequestDto groupChatRequestDto) {

        try {
            GroupChatResponseDto groupChatResponseDto = GroupChatResponseDto.builder()
                    .memberId(groupChatRequestDto.getMemberId())
                    .groupChatRoomId(groupChatRequestDto.getGroupChatRoomId())
                    .chatMessage(groupChatRequestDto.getChatMessage())
                    .isJoinNotice(false)
                    .build();

            stompMessageSend(groupChatResponseDto);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean groupChatRoomCreate(GroupChatRequestDto groupChatRequestDto) {

        try {
            chatDao.insertGroupChatRoom(groupChatRequestDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean joinGroupChatRoom(GroupChatRequestDto groupChatRequestDto) {

        try {
            chatDao.insertGroupChatRoomMember(groupChatRequestDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean joinGroupChatMessageSend(GroupChatRequestDto groupChatRequestDto) {

        try {
            GroupChatResponseDto groupChatResponseDto = GroupChatResponseDto.builder()
                    .memberId(groupChatRequestDto.getMemberId())
                    .groupChatRoomId(groupChatRequestDto.getGroupChatRoomId())
                    .isJoinNotice(true)
                    .build();

            stompMessageSend(groupChatResponseDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean groupChatRoomExit(GroupChatRequestDto groupChatRequestDto) {
        try {
            chatDao.deleteGroupChatRoomMember(groupChatRequestDto);

            GroupChatResponseDto groupChatResponseDto = GroupChatResponseDto.builder()
                    .memberId(groupChatRequestDto.getMemberId())
                    .groupChatRoomId(groupChatRequestDto.getGroupChatRoomId())
                    .isExitNotice(true)
                    .build();

            stompMessageSend(groupChatResponseDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public GroupChatResponseDto getGroupChatRoomList(GroupChatRequestDto groupChatRequestDto) {

        List<GroupChatRoom> groupChatResponseDtoList = new ArrayList<>();
        groupChatRequestDto.setOffset(groupChatRequestDto.getOffset());

        int totalCount = chatDao.selectGroupChatRoomTotalCount(groupChatRequestDto);

        if (totalCount != 0) {
            groupChatResponseDtoList = chatDao.selectGroupChatRoom(groupChatRequestDto);
        }

        return GroupChatResponseDto.builder()
                .groupChatRoomList(groupChatResponseDtoList)
                .totalCount(totalCount)
                .build();
    }
}
