package com.example.globalStudents.domain.board.dto;

import lombok.Getter;

public class CommentRequestDTO {

    @Getter
    public static class CreateCommentDTO {
        String postId;
        String content;
        Boolean isAnonymous;
    }

    @Getter
    public static class LikeCommentDTO {
        String commentId;
    }

    @Getter
    public static class DeleteCommentDTO {
        String commentId;
    }
}
