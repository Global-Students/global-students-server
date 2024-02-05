package com.example.globalStudents.domain.chat.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "chat_room_user")
@IdClass(ChatRoomUserPK.class)
public class ChatRoomUserEntity {
    @Id
    @Column(name = "chat_room_id")
    private Long chatRoomId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @CreatedDate
    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime updatedAt;

}
