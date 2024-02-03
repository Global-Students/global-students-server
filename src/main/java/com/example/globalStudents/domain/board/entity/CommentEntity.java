package com.example.globalStudents.domain.board.entity;

import com.example.globalStudents.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(300)")
    private String body;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isAnonymous;

    @CreatedDate
    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<ReportEntity> reportEntityList = new ArrayList<>();
}
