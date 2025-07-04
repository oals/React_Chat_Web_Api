package com.example.chatx_api.service;

import com.example.chatx_api.dao.ChatDao;
import com.example.chatx_api.dto.GroupChatRoom;
import com.example.chatx_api.dto.response.GroupChatResponseDto;
import com.example.chatx_api.dto.request.GroupChatRequestDto;
import com.example.chatx_api.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroupChatServiceImpl implements GroupChatService{

    private final SimpMessagingTemplate messagingTemplate;
    private final RedisService redisService;
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
            redisService.syncGroupChatMemberCountToZSet(groupChatRequestDto);
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
    public GroupChatResponseDto getGroupChatRoomList(GroupChatRequestDto groupChatRequestDto) {

        List<GroupChatRoom> groupChatResponseDtoList = new ArrayList<>();

        int totalCount = chatDao.selectGroupChatRoomTotalCount(groupChatRequestDto);

        if (totalCount != 0) {
            Map<Long, Integer> groupChatRoomIdList = redisService.getGroupChatRoomIdsOrderByMemberCountDesc(groupChatRequestDto);

            List<Long> roomIdList = new ArrayList<>(groupChatRoomIdList.keySet());
            List<Integer> roomMemberCountList = new ArrayList<>(groupChatRoomIdList.values());;

            groupChatRequestDto.setGroupChatRoomIdList(roomIdList);
            groupChatResponseDtoList = chatDao.selectGroupChatRoom(groupChatRequestDto);

            for ( int i = 0; i < groupChatResponseDtoList.size(); i++ ) {
                int memberCount = 0;

                if (i < roomMemberCountList.size()) {
                    memberCount = roomMemberCountList.get(i);
                }

                groupChatResponseDtoList.get(i).setCurrentParticipants(memberCount);
            }
        }

        return GroupChatResponseDto.builder()
                .groupChatRoomList(groupChatResponseDtoList)
                .totalCount(totalCount)
                .build();
    }
}
