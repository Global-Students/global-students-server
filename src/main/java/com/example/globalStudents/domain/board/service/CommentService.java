package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.dto.CommentRequestDTO;
import com.example.globalStudents.domain.board.entity.CommentEntity;
import com.example.globalStudents.domain.board.entity.CommentLikeEntity;

public interface CommentService {

    public CommentEntity writeComment(CommentRequestDTO.CreateCommentDTO request);

    public CommentLikeEntity likeComment(CommentRequestDTO.LikeCommentDTO request);

}
