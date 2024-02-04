package com.example.globalStudents.domain.board.entity;

import com.example.globalStudents.domain.board.enums.UserPostReactionType;
import com.example.globalStudents.domain.board.id.UserPostReactionID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_post_reaction")
public class UserPostReactionEntity {

    @EmbeddedId
    private UserPostReactionID id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private UserPostReactionType type;

}
