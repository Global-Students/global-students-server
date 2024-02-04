package com.example.globalStudents.domain.board.entity;

import com.example.globalStudents.domain.board.id.CommentLikeID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "comment_like")
public class CommentLikeEntity {

    @EmbeddedId
    private CommentLikeID id;

}
