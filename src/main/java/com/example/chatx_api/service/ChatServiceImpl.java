package com.example.chatx_api.service;

import com.example.chatx_api.dao.ChatDao;
import com.example.chatx_api.dto.ChatArchiveDto;
import com.example.chatx_api.dto.ChatArchiveResponseDto;
import com.example.chatx_api.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatDao chatDao;

    @Override
    public boolean chatSend(ChatMessageDto chatMessageDto) {

        try {
            System.out.println("소켓 전송 경로 : /topic/chat/" + chatMessageDto.getChatRoomId());
            String destination = "/topic/chat/" + chatMessageDto.getChatRoomId();
            messagingTemplate.convertAndSend(destination, chatMessageDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean chatArchiveSave(ChatArchiveDto chatArchiveDto) {

        try {
            chatArchiveDto.setChatArchiveBookmarks(false);
            chatDao.insertChatArchive(chatArchiveDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ChatArchiveResponseDto getChatArchive(ChatArchiveDto chatArchiveDto) {
        try {
            List<ChatArchiveDto> chatArchiveDtoList = new ArrayList<>();
            chatArchiveDto.setOffset(chatArchiveDto.getOffset());

            int totalCount = chatDao.selectChatArchiveTotalCount(chatArchiveDto);

            if (totalCount != 0) {
                chatArchiveDtoList = chatDao.selectChatArchive(chatArchiveDto);
            }

            return ChatArchiveResponseDto.builder()
                    .chatArchiveDtoList(chatArchiveDtoList)
                    .totalCount(totalCount)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getChatArchiveMessage(ChatArchiveDto chatArchiveDto) {






        return "";
    }

    @Override
    public boolean setChatArchiveBookmarks(ChatArchiveDto chatArchiveDto) {

        try {
            chatDao.updateChatArchiveBookmarks(chatArchiveDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delChatArchive(ChatArchiveDto chatArchiveDto) {
        try {
            chatDao.deleteChatArchive(chatArchiveDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
