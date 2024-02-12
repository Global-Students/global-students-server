package com.example.globalStudents.domain.board.id;

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
