package com.example.globalStudents.domain.board.converter;

import com.example.globalStudents.domain.board.dto.BoardResponseDTO;
import com.example.globalStudents.domain.board.entity.PostEntity;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BoardConverter {

    public static BoardResponseDTO.BoardResultDTO toBoardResultDTO(
            BoardResponseDTO.PageInfoDTO pageInfo, BoardResponseDTO.NoticePostDTO noticePost,
            List<BoardResponseDTO.PopularPostDTO> popular, List<BoardResponseDTO.PostDTO> posts) {

        return BoardResponseDTO.BoardResultDTO.builder()
                .pageInfo(pageInfo)
                .noticePost(noticePost)
                .popular(popular)
                .posts(posts)
                .build();
    }

    public static BoardResponseDTO.NoticePostDTO toNoticePostDTO(PostEntity post) {
        return BoardResponseDTO.NoticePostDTO.builder()
                .title(post.getTitle())
                .postId(post.getId().toString())
                .build();
    }

    public static List<BoardResponseDTO.PopularPostDTO> toPopularPostDTOList(Page<PostEntity> postList) {

        return postList.stream()
                .map(BoardConverter::toPopularPostDTO).collect(Collectors.toList());
    }

    public static List<BoardResponseDTO.PostDTO> toPostDTOList(Page<PostEntity> postList) {
        return postList.stream()
                .map(BoardConverter::toPostDTO).collect(Collectors.toList());
    }

    public static BoardResponseDTO.PopularPostDTO toPopularPostDTO(PostEntity post) {
        return BoardResponseDTO.PopularPostDTO.builder()
                .title(post.getTitle())
                .likes(post.getLikes())
                .postId(post.getId().toString())
                .build();
    }

    public static BoardResponseDTO.PostDTO toPostDTO(PostEntity post) {
        return BoardResponseDTO.PostDTO.builder()
                .title(post.getTitle())
                .numberOfComments(post.getCommentList().size())
                .date(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .author((post.getIsAnonymous()) ? "익명" : post.getUid())
                .likes(post.getLikes())
                .views(post.getView())
                .hasImage((post.getPostImageList().isEmpty()) ? false : true)
                .postId(post.getId().toString())
                .build();
    }

}
