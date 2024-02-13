package com.example.globalStudents.domain.board.converter;

import com.example.globalStudents.domain.board.dto.CommentRequestDTO;
import com.example.globalStudents.domain.board.dto.CommentResponseDTO;
import com.example.globalStudents.domain.board.entity.CommentEntity;
import com.example.globalStudents.domain.board.entity.CommentLikeEntity;
import com.example.globalStudents.domain.board.enums.CommentStatus;
import com.example.globalStudents.domain.user.entity.UserEntity;

import java.time.LocalDateTime;

public class CommentConverter {

    public static CommentResponseDTO.CreateCommentResultDTO toCreateCommentResultDTO(CommentEntity comment) {
        return CommentResponseDTO.CreateCommentResultDTO.builder()
                .commentId(comment.getId().toString())
                .build();
    }

    public static CommentEntity toComment(CommentRequestDTO.CreateCommentDTO request) {
        return CommentEntity.builder()
                .body(request.getContent())
                .isAnonymous(request.getIsAnonymous())
                .likes(0)
                .status(CommentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static CommentResponseDTO.LikeCommentResultDTO toLikeCommentResultDTO(CommentLikeEntity commentLike) {
        return CommentResponseDTO.LikeCommentResultDTO.builder()
                .commentId(commentLike.getComment().getId().toString())
                .build();
    }

    public static CommentLikeEntity toCommentLike(UserEntity user, CommentEntity comment) {
        return CommentLikeEntity.builder()
                .user(user)
                .comment(comment)
                .build();
    }
}
