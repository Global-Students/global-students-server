package com.example.globalStudents.domain.chat.converter;

import com.example.globalStudents.domain.chat.dto.*;
import com.example.globalStudents.domain.chat.entity.ChatRoomEntity;
import com.example.globalStudents.domain.chat.entity.ChatRoomUserEntity;
import com.example.globalStudents.domain.chat.entity.MessageEntity;
import com.example.globalStudents.domain.chat.repository.ChatRoomRepository;
import com.example.globalStudents.domain.chat.repository.ChatRoomUserRepository;
import com.example.globalStudents.domain.chat.repository.MessageRepository;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Handler;

@Service
@RequiredArgsConstructor
public class ChatConverter {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final MessageRepository messageRepository;

    public ChatListResponse toChatListResponse(List<ChatRoomUserEntity> chatList){
        List<chatRoomDTO> chatRoomList = new ArrayList<>();
        for(ChatRoomUserEntity chat : chatList){
            ChatRoomEntity chatRoom = chat.getChatRoom();
            UserEntity freind = chatRoomUserRepository.findByChatRoomIdAndUserIdNot(chat.getChatRoom(), chat.getUser()).getUser();
            Optional<MessageEntity> lastestMessage = messageRepository.findTopByChatRoomIdOrderByCreatedAtDesc(chatRoom.getId());
            if(lastestMessage.isEmpty()){
                throw new ExceptionHandler(ErrorStatus.CHAT_NOT_FOUND);
            }
            MessageEntity message = lastestMessage.get();

            chatRoomDTO dto = chatRoomDTO.builder()
                    .chatRoomId(chatRoom.getId().toString())
                    .friendName(freind.getNickname())
                    .latestMessage(message.getBody())
                    .latestMessageTime(message.getCreatedAt())
                    .readYn(message.getRead() ? "Y" : "N")
                    //.imageUrl()
                    .build();

            chatRoomList.add(dto);
        }
        return ChatListResponse.builder()
                .totalChat(chatList.size())
                .chatRooms(chatRoomList)
                .build();

    }

    public ChatReadResponse toChatReadResponse(List<MessageEntity> messageList, String userId) {
        List<messageDTO> messages = new ArrayList<>();
        for(MessageEntity message : messageList){
            String message_userId = message.getUser().getUserId();
            messageDTO dto = messageDTO.builder()
                    .userId(message_userId)
                    .sendYn(userId.equals(message_userId) ? "Y" : "N")
                    .text(message.getBody())
                    .createdAt(message.getCreatedAt())
                    .build();
        }
        return ChatReadResponse.builder()
                .page(0)
                .size(100)
                .totalPage(1)
                .totalMessage(messageList.size())
                .messages(messages)
                .build();
    }

    public ChatCreateResponse toChatCreateResponse(String chatRoomId) {
        return ChatCreateResponse.builder()
                .chatRoomId(chatRoomId)
                .build();

    }

    public ChatSendResponse toChatSendResponse(MessageEntity message) {
        return ChatSendResponse.builder()
                .messageId(message.getId().toString())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
