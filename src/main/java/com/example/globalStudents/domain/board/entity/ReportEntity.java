package com.example.globalStudents.domain.board.entity;

import com.example.globalStudents.domain.board.enums.ReportStatus;
import com.example.globalStudents.domain.board.enums.ReportType;
import com.example.globalStudents.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "report")
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

    public void setUser(UserEntity user) {
        if (this.user != null) {
            this.user.getReportList().remove(this);
        }
        this.user = user;
        user.getReportList().add(this);
    }

    public void setPost(PostEntity post) {
        if (this.post != null) {
            this.post.getReportList().remove(this);
        }
        this.post = post;
        post.getReportList().add(this);
    }

    public void setComment(CommentEntity comment) {
        if (this.comment != null) {
            this.comment.getReportList().remove(this);
        }
        this.comment = comment;
        comment.getReportList().add(this);
    }
}
