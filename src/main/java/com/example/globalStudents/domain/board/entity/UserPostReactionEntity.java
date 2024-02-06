package com.example.globalStudents.domain.board.entity;

import com.example.globalStudents.domain.board.enums.UserPostReactionType;
import com.example.globalStudents.domain.board.id.UserPostReactionID;
import com.example.globalStudents.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_post_reaction")
@IdClass(UserPostReactionID.class)
public class UserPostReactionEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private UserPostReactionType type;

    public void setPost(PostEntity post) {
        if (this.post != null) {
            this.post.getUserPostReactionList().remove(this);
        }
        this.post = post;
        post.getUserPostReactionList().add(this);
    }

    public void setUser(UserEntity user) {
        if (this.user != null) {
            this.user.getUserPostReactionList().remove(this);
        }
        this.user = user;
        user.getUserPostReactionList().add(this);
    }
}
