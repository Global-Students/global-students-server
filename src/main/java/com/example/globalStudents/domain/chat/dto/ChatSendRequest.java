package com.example.globalStudents.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatSendRequest {
    public String chatRoomId;
    public String userId;
    public String message;
}
