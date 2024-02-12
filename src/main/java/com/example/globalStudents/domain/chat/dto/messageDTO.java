package com.example.globalStudents.domain.chat.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class messageDTO {
    public String userId;
    public String sendYn;
    public String text;
    public LocalDateTime createdAt;
}
