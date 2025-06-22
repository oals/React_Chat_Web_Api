package com.example.chatx_api.service;

import com.example.chatx_api.dto.ChatDto;

public interface ChatService {

    boolean chatSend(ChatDto chatDto);
}
