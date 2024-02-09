package com.example.globalStudents.domain.chat.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class chatRoomDTO {
    public String chatRoomId;
    public String friendName;
    public String latestMessage;
    public LocalDateTime latestMessageTime;
    public String readYn;
    public String imageUrl;
}
