package com.example.globalStudents.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatReadResponse {
    public Integer page;
    public Integer size;
    public Integer totalPage;
    public Integer totalMessage;
    public List<messageDTO> messages;
}
