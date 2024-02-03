package com.example.globalStudents.domain.board.entity;

import com.example.globalStudents.domain.board.enums.ReportStatus;
import com.example.globalStudents.domain.board.enums.ReportType;
import com.example.globalStudents.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
    private ReportType type;

    @Column(columnDefinition = "VARCHAR(100)")
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
    private ReportStatus status;

    @CreatedDate
    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;
}
