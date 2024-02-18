package com.example.globalStudents.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class BoardResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardInfoDTO {
        String boardName;
        String detail;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageInfoDTO {
        int page;
        int size;
        int totalPage;
        int totalPost;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopularPostDTO {
        String title;
        int likes;
        String postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostDTO {
        String title;
        int numberOfComments;
        String date;
        String author;
        int likes;
        int views;
        Boolean hasImage;
        String postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticePostDTO {
        String title;
        String postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardResultDTO {
        BoardInfoDTO boardInfo;
        PageInfoDTO pageInfo;
        NoticePostDTO noticePost;
        List<PopularPostDTO> popular;
        List<PostDTO> posts;
    }
}
