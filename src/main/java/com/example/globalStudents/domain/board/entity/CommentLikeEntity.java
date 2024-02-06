package com.example.globalStudents.domain.board.entity;

import com.example.globalStudents.domain.board.id.CommentLikeID;
import com.example.globalStudents.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "comment_like")
@IdClass(CommentLikeID.class)
public class CommentLikeEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void setComment(CommentEntity comment) {
        if (this.comment != null) {
            this.comment.getCommentLikeList().remove(this);
        }
        this.comment = comment;
        comment.getCommentLikeList().add(this);
    }

    public void setUser(UserEntity user) {
        if (this.user != null) {
            this.user.getCommentLikeList().remove(this);
        }
        this.user = user;
        user.getCommentLikeList().add(this);
    }
}
