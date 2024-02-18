package com.example.globalStudents.domain.myPage.converter;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.enums.CommentStatus;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class MypageConverter {

    public static MypageResponseDTO.PostDTO toPostDTO(PostEntity post) {
        return MypageResponseDTO.PostDTO.builder()
                .title(post.getTitle())
                .numberOfComments(post.getCommentList().stream().filter(comment -> comment.getStatus() == CommentStatus.ACTIVE).collect(Collectors.toList()).size())
                .date(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .author(post.getUid())
                .likes(post.getLikes())
                .views(post.getView())
                .hasImage((post.getPostImageList().isEmpty()) ? false : true)
                .postId(post.getId().toString())
                .boardId(post.getBoard().getId().toString())
                .build();
    }

}
