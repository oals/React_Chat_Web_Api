package com.example.chatx_api.redis;

import com.example.chatx_api.dto.request.GroupChatRequestDto;
import com.example.chatx_api.dto.response.GroupChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{

    private final RedisTemplate<String,String> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    private void stompMessageSend(GroupChatResponseDto groupChatResponseDto) {
        System.out.println("전송 경로 : /topic/groupChat/" + groupChatResponseDto.getGroupChatRoomId());
        String destination = "/topic/groupChat/" + groupChatResponseDto.getGroupChatRoomId();
        messagingTemplate.convertAndSend(destination, groupChatResponseDto);
    }

    @Override
    public void syncGroupChatMemberCountToZSet(GroupChatRequestDto groupChatRequestDto) {
        String key = "groupChatRoom:" + groupChatRequestDto.getGroupChatRoomId();
        Long roomId = groupChatRequestDto.getGroupChatRoomId();
        Long count = redisTemplate.opsForSet().size(key);

        double compositeScore = count * 1_000_000_000L + roomId;

        redisTemplate.opsForZSet().add("groupChatRoom:memberCounts", roomId.toString(), compositeScore);
    }

    @Override
    public boolean joinGroupChatRoom(GroupChatRequestDto groupChatRequestDto) {
        try {
            redisTemplate.opsForSet().add("groupChatRoom:" + groupChatRequestDto.getGroupChatRoomId(), groupChatRequestDto.getMemberId().toString());
            syncGroupChatMemberCountToZSet(groupChatRequestDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean groupChatRoomExit(GroupChatRequestDto groupChatRequestDto) {
        try {
            String key = "groupChatRoom:" + groupChatRequestDto.getGroupChatRoomId();
            Long roomId = groupChatRequestDto.getGroupChatRoomId();
            Long memberId = groupChatRequestDto.getMemberId();

            redisTemplate.opsForSet().remove(key, memberId.toString());

            Long count = redisTemplate.opsForSet().size(key);

            double compositeScore = count * 1_000_000_000L + roomId;

            redisTemplate.opsForZSet().add("groupChatRoom:memberCounts", roomId.toString(), compositeScore);

            GroupChatResponseDto responseDto = GroupChatResponseDto.builder()
                    .memberId(memberId)
                    .groupChatRoomId(roomId)
                    .isExitNotice(true)
                    .build();

            stompMessageSend(responseDto);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<Long, Integer> getGroupChatRoomIdsOrderByMemberCountDesc(GroupChatRequestDto groupChatRequestDto) {

        groupChatRequestDto.setOffset(groupChatRequestDto.getOffset());

        Set<ZSetOperations.TypedTuple<String>> resultSet = redisTemplate.opsForZSet()
                        .reverseRangeWithScores("groupChatRoom:memberCounts", groupChatRequestDto.getOffset(),groupChatRequestDto.getOffset() + (groupChatRequestDto.getSize() - 1));

        return resultSet.stream()
                .collect(Collectors.toMap(
                        t -> Long.parseLong(t.getValue()),
                        t -> (int)(t.getScore() / 1_000_000_000L),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    @Override
    public Long getGroupChatRoomMemberCount(GroupChatRequestDto groupChatRequestDto) {
        try {
            Double score = redisTemplate.opsForZSet().score("groupChatRoom:memberCounts", groupChatRequestDto.getGroupChatRoomId().toString());

            if (score != null) {
                return (long)(score / 1_000_000_000L);
            } else {
                return 0L;
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
