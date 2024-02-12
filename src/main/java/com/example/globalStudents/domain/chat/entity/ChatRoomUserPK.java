package com.example.globalStudents.domain.chat.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChatRoomUserPK implements Serializable {
    private Long chatRoom;
    private Long user;
}