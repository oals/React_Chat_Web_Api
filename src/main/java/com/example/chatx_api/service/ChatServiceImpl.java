package com.example.chatx_api.service;

import com.example.chatx_api.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public boolean chatSend(ChatDto chatDto) {

        try {
            System.out.println("소켓 전송 경로 : /topic/chat/" + chatDto.getChatRoomId());
            String destination = "/topic/chat/" + chatDto.getChatRoomId();
            messagingTemplate.convertAndSend(destination, chatDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
