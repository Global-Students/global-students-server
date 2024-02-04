package com.example.globalStudents.domain.chat.service;

import com.example.globalStudents.domain.chat.dto.*;
import com.example.globalStudents.domain.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    public ChatListResponse getChatList(String userId) {
    }

    public ChatCreateResponse createChatRoom(ChatCreateRequest chatCreateRequest) {
    }

    public ChatReadResponse getChatRoom(String userId, String chatRoomId) {
    }

    public ChatSendResponse sendMessage(String chatRoomId, ChatSendRequest chatSendRequest) {
    }
}
