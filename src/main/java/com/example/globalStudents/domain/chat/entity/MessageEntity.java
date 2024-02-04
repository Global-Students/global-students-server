package com.example.globalStudents.domain.chat.entity;

import com.example.globalStudents.domain.user.entity.User;
import com.example.globalStudents.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //연관 관계
    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoomEntity chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false, columnDefinition = "VARCHAR(45)")
    private String body;


    @Column(columnDefinition = "TINYINT(1)")
    private Boolean read;

    @CreatedDate
    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime updatedAt;


}
