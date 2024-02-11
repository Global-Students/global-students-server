package com.example.globalStudents.domain.chat.service;

import com.example.globalStudents.domain.chat.converter.ChatConverter;
import com.example.globalStudents.domain.chat.dto.*;
import com.example.globalStudents.domain.chat.entity.ChatRoomEntity;
import com.example.globalStudents.domain.chat.entity.ChatRoomUserEntity;
import com.example.globalStudents.domain.chat.entity.MessageEntity;
import com.example.globalStudents.domain.chat.repository.ChatRoomRepository;
import com.example.globalStudents.domain.chat.repository.ChatRoomUserRepository;
import com.example.globalStudents.domain.chat.repository.MessageRepository;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final MessageRepository messageRepository;
    private final ChatConverter chatConverter;
    private final UserRepository userRepository;

    public ChatListResponse getChatList(String userId) {
        UserEntity user = userRepository.findById(Long.parseLong(userId)).orElse(null); // userId에 해당하는 사용자를 가져옴
        List<ChatRoomUserEntity> chatList = chatRoomUserRepository.findByUser(user); // 해당 사용자와 관련된 ChatRoomUserEntity 목록을 가져옴

        ChatListResponse response = chatConverter.toChatListResponse(chatList);
        return response;
    }

    public ChatCreateResponse createChatRoom(ChatCreateRequest chatCreateRequest) {
        // 채팅방 생성
        ChatRoomEntity chatRoom = ChatRoomEntity.builder()
                .status("Y")
                .createdAt(LocalDateTime.now())
                .build();
        chatRoom = chatRoomRepository.save(chatRoom);

        String chatRoomId = chatRoom.getId().toString();

        // chat_room_user 저장
        UserEntity user = userRepository.findByUserId(chatCreateRequest.getUserId()).orElse(null);
        UserEntity freind = userRepository.findByUserId(chatCreateRequest.getFreindId()).orElse(null);

        ChatRoomUserEntity chatRoomUser = ChatRoomUserEntity.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();
        chatRoomUserRepository.save(chatRoomUser);
        chatRoomUser = ChatRoomUserEntity.builder()
                .chatRoom(chatRoom)
                .user(freind)
                .build();
        chatRoomUserRepository.save(chatRoomUser);

        // 메시지 생성
        MessageEntity message = MessageEntity.builder()
                .chatRoom(chatRoom)
                .user(user)
                .body(chatCreateRequest.getMessage())
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();
        messageRepository.save(message);

        ChatCreateResponse response = chatConverter.toChatCreateResponse(chatRoomId);
        return response;
    }

    public ChatReadResponse getChatRoom(String userId, String chatRoomId) {
        //메세지 조회
        List<MessageEntity> messageList = messageRepository.findByChatRoomId(Long.parseLong(chatRoomId));
        List<MessageEntity> notReadMessages = messageRepository.findByUserIdNotAndReadFalse(Long.parseLong(userId));

        //메세지 읽음 처리
        for(MessageEntity message : notReadMessages){
            message.setRead(true);
        }
        messageRepository.saveAll(notReadMessages);

        ChatReadResponse response = chatConverter.toChatReadResponse(messageList, userId);
        return response;
    }

    public ChatSendResponse sendMessage(String chatRoomId, ChatSendRequest chatSendRequest) {
        ChatRoomEntity chatRoom = chatRoomRepository.findById(Long.parseLong(chatRoomId)).orElse(null);
        UserEntity user = userRepository.findByUserId(chatSendRequest.getUserId()).orElse(null);

        // 메시지 생성
        MessageEntity message = MessageEntity.builder()
                .chatRoom(chatRoom)
                .user(user)
                .body(chatSendRequest.getMessage())
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();
        message = messageRepository.save(message);

        ChatSendResponse response = chatConverter.toChatSendResponse(message);
        return response;
    }
}
