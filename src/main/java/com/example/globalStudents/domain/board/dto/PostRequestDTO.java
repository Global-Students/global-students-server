package com.example.globalStudents.domain.board.dto;

import lombok.Getter;

import java.util.List;

public class PostRequestDTO {

    @Getter
    public static class WritePostDTO {
        String boardId;
        String title;
        String content;
        Boolean isAnonymous;

        List<PostRequestImageDTO> image;

    }

    @Getter
    public static class PostRequestImageDTO {
        String imageId;
    }

    @Getter
    public static class ReactPostDTO {
        String postId;
        String reactionType;
    }

    @Getter
    public static class DeletePostDTO {
        String postId;
    }

}
