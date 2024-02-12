package com.example.globalStudents.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PostResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WritePostResultDTO {
        String postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePostResultDTO {
        String postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageDTO {
        String imageId;
        String fileName;
        String imageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentDTO {
        String userId;
        String nickname;
        Boolean isAnonymous;
        String content;
        int likes;
        String commentId;
        String date;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetPostResultDTO {
        String postId;
        String title;
        String date;
        String userId;
        String userNickname;
        Boolean isAnonymous;
        int views;
        List<ImageDTO> images;
        int likes;
        int bookmarks;
        String content;
        List<CommentDTO> comment;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReactPostResultDTO {
        String postId;
    }

}
