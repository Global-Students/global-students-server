package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.dto.CommentRequestDTO;
import com.example.globalStudents.domain.board.dto.CommentResponseDTO;

public interface CommentService {

    public CommentResponseDTO.CreateCommentResultDTO writeComment(CommentRequestDTO.CreateCommentDTO request);

    public CommentResponseDTO.LikeCommentResultDTO likeComment(CommentRequestDTO.LikeCommentDTO request);

}
