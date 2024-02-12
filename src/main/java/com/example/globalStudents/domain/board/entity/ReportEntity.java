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
    @JoinColumn(name = "report_user_id")
    private UserEntity reportUser;

    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    private UserEntity reportedUser;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    public void setReportUser(UserEntity user) {
        if (this.reportUser != null) {
            this.reportUser.getReportList().remove(this);
        }
        this.reportUser = user;
        user.getReportList().add(this);
    }

    public void setReportedUser(UserEntity user) {
        if (this.reportedUser != null) {
            this.reportedUser.getReportedList().remove(this);
        }
        this.reportedUser = user;
        user.getReportedList().add(this);
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
