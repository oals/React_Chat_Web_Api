package com.example.chatx_api.service;

import com.example.chatx_api.dao.ChatDao;
import com.example.chatx_api.dto.request.ChatArchiveRequestDto;
import com.example.chatx_api.dto.response.ChatArchiveResponseDto;
import com.example.chatx_api.dto.request.ChatRequestDto;
import com.example.chatx_api.mongo.ChatArchive;
import com.example.chatx_api.mongo.MongoService;
import com.example.chatx_api.mongo.MongoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final SimpMessagingTemplate messagingTemplate;
    private final MongoService mongoService;
    private final ChatDao chatDao;

    @Override
    public boolean chatSend(ChatRequestDto chatRequestDto) {

        try {
            System.out.println("소켓 전송 경로 : /topic/chat/" + chatRequestDto.getChatRoomId());
            String destination = "/topic/chat/" + chatRequestDto.getChatRoomId();
            messagingTemplate.convertAndSend(destination, chatRequestDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean chatArchiveSave(ChatArchiveRequestDto chatArchiveRequestDto) {

        try {
            chatArchiveRequestDto.setChatArchiveBookmarks(false);
            chatDao.insertChatArchive(chatArchiveRequestDto);

            ChatArchive archive = new ChatArchive();
            archive.setChatArchiveId(chatArchiveRequestDto.getChatArchiveId());
            archive.setChatArchiveJson(chatArchiveRequestDto.getChatArchiveJson());
            archive.setChatArchiveCreateDate(LocalDateTime.now());

            mongoService.insertChatArchive(archive);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ChatArchiveResponseDto getChatArchive(ChatArchiveRequestDto chatArchiveRequestDto) {
        try {
            List<com.example.chatx_api.dto.ChatArchive> chatArchiveDtoList = new ArrayList<>();
            chatArchiveRequestDto.setOffset(chatArchiveRequestDto.getOffset());

            int totalCount = chatDao.selectChatArchiveTotalCount(chatArchiveRequestDto);

            if (totalCount != 0) {
                chatArchiveDtoList = chatDao.selectChatArchive(chatArchiveRequestDto);
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
    public boolean setChatArchiveBookmarks(ChatArchiveRequestDto chatArchiveRequestDto) {

        try {
            chatDao.updateChatArchiveBookmarks(chatArchiveRequestDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delChatArchive(ChatArchiveRequestDto chatArchiveRequestDto) {
        try {
            chatDao.deleteChatArchive(chatArchiveRequestDto);
            mongoService.deleteChatArchive(chatArchiveRequestDto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
