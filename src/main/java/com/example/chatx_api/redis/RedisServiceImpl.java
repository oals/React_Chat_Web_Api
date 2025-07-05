package com.example.chatx_api.redis;

import com.example.chatx_api.dto.request.GroupChatRequestDto;
import com.example.chatx_api.dto.response.GroupChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        String key = "groupChatRoom:" + groupChatRequestDto.getGroupChatRoomId() + "::" + groupChatRequestDto.getGroupChatRoomTopic();
        Long roomId = groupChatRequestDto.getGroupChatRoomId();
        Long count = redisTemplate.opsForSet().size(key);

        double compositeScore = count * 1_000_000_000L + roomId;

        String memberValue = roomId + "::" + groupChatRequestDto.getGroupChatRoomTopic();

        redisTemplate.opsForZSet().add("groupChatRoom:memberCounts", memberValue, compositeScore);

    }

    @Override
    public boolean joinGroupChatRoom(GroupChatRequestDto groupChatRequestDto) {
        try {
            String roomKey = "groupChatRoom:" + groupChatRequestDto.getGroupChatRoomId() + "::" + groupChatRequestDto.getGroupChatRoomTopic();
            redisTemplate.opsForSet()
                    .add(roomKey, groupChatRequestDto.getMemberId().toString());

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
            String key = "groupChatRoom:" + groupChatRequestDto.getGroupChatRoomId() + "::" + groupChatRequestDto.getGroupChatRoomTopic();
            Long roomId = groupChatRequestDto.getGroupChatRoomId();
            Long memberId = groupChatRequestDto.getMemberId();

            redisTemplate.opsForSet().remove(key, memberId.toString());

            Long count = redisTemplate.opsForSet().size(key);

            double compositeScore = count * 1_000_000_000L + roomId;

            redisTemplate.opsForZSet().add("groupChatRoom:memberCounts", roomId + "::" + groupChatRequestDto.getGroupChatRoomTopic(), compositeScore);

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
        Set<ZSetOperations.TypedTuple<String>> fullSet = redisTemplate.opsForZSet()
                .reverseRangeWithScores("groupChatRoom:memberCounts", 0, -1);

        List<Map.Entry<Long, Integer>> filtered = fullSet.stream()
                .map(t -> {
                    String[] parts = t.getValue().split("::", 2);
                    Long roomId = Long.parseLong(parts[0]);
                    String topic = parts.length > 1 ? parts[1] : "";
                    int memberCount = (int)(t.getScore() / 1_000_000_000L);
                    return new AbstractMap.SimpleEntry<>(roomId, new AbstractMap.SimpleEntry<>(topic, memberCount));
                })
                .filter(entry -> {
                    String target = groupChatRequestDto.getGroupChatRoomTopic();
                    return target == null || target.equals("전체") || target.equals(entry.getValue().getKey());
                })
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().getValue()))
                .toList();

        Stream<Map.Entry<Long, Integer>> stream = filtered.stream();

        if (Objects.equals(groupChatRequestDto.getSearchText(), "")) {
            stream = stream
                    .skip(groupChatRequestDto.getOffset())
                    .limit(groupChatRequestDto.getSize());
        } else {
            stream = stream.skip(groupChatRequestDto.getOffset());
        }

        return stream.collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> b,
                LinkedHashMap::new
        ));

    }

    @Override
    public Long getGroupChatRoomMemberCount(GroupChatRequestDto groupChatRequestDto) {
        try {
            String memberValue = groupChatRequestDto.getGroupChatRoomId() + "::" + groupChatRequestDto.getGroupChatRoomTopic();
            Double score = redisTemplate.opsForZSet().score("groupChatRoom:memberCounts", memberValue);

            return score != null ? (long)(score / 1_000_000_000L) : 0L;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
