package com.example.globalStudents.domain.board.id;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserPostReactionID implements Serializable {
    private Long post;
    private Long user;
}
