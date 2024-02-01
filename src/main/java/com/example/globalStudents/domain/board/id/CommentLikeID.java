package com.example.globalStudents.domain.board.id;

import com.example.globalStudents.domain.board.entity.CommentEntity;
import com.example.globalStudents.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class CommentLikeID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity commentEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
