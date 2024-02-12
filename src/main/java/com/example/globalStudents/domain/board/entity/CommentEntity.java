package com.example.globalStudents.domain.board.entity;

import com.example.globalStudents.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(300)")
    private String body;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isAnonymous;

    @Column(nullable = false)
    private Integer likes;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<ReportEntity> reportList = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLikeEntity> commentLikeList = new ArrayList<>();

    public void setUser(UserEntity user) {
        if (this.user != null) {
            this.user.getCommentList().remove(this);
        }
        this.user = user;
        user.getCommentList().add(this);
    }

    public void setPost(PostEntity post) {
        if (this.post != null) {
            this.post.getCommentList().remove(this);
        }
        this.post = post;
        post.getCommentList().add(this);
    }

    public void incrementLikes() {
        this.likes++;
    }
}
