package com.example.globalStudents.domain.chat.entity;


import com.example.globalStudents.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "chat_room")
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @CreatedDate
    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime updatedAt;

    // 연관관계
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<MessageEntity> MessageList = new ArrayList<>();
}
