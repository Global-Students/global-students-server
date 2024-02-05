package com.example.globalStudents.domain.chat.entity;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChatRoomUserPK implements Serializable {
    private Long chatRoomId;
    private Long userId;
}
