package com.example.chatx_api.redis;

import com.example.chatx_api.dto.request.GroupChatRequestDto;
import java.util.Map;

public interface RedisService {

    Map<Long, Integer> getGroupChatRoomIdsOrderByMemberCountDesc(GroupChatRequestDto groupChatRequestDto);

    void syncGroupChatMemberCountToZSet(GroupChatRequestDto groupChatRequestDto);

    boolean joinGroupChatRoom(GroupChatRequestDto groupChatRequestDto);

    boolean groupChatRoomExit(GroupChatRequestDto groupChatRequestDto);

    Long getGroupChatRoomMemberCount(GroupChatRequestDto groupChatRequestDto);
}
