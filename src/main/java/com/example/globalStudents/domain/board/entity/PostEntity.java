package com.example.globalStudents.domain.board.entity;

import com.example.globalStudents.domain.board.enums.PostStatus;
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
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(30)")
    private String uid;

    @Column(columnDefinition = "VARCHAR(100)")
    private String title;

    @Column(columnDefinition = "VARCHAR(5000)")
    private String body;

    @Column(nullable = false)
    private Integer view;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
    private PostStatus status;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isAnonymous;

    @CreatedDate
    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImageEntity> postImageList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private  List<UserPostReactionEntity> userPostReactionList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<ReportEntity> reportList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentEntity> commentList = new ArrayList<>();

    public void setBoard(BoardEntity board) {
        if (this.board != null) {
            this.board.getPostList().remove(this);
        }
        this.board = board;
        board.getPostList().add(this);
    }

    public void setUser(UserEntity user) {
        if (this.user != null) {
            this.user.getPostList().remove(this);
        }
        this.user = user;
        user.getPostList().add(this);
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String content) {
        this.body = content;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public void incrementView() {
        this.view++;
    }
}
