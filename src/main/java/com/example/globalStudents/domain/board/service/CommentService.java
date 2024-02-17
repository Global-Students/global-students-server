package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.dto.CommentRequestDTO;
import com.example.globalStudents.domain.board.dto.CommentResponseDTO;

public interface CommentService {

    public CommentResponseDTO.CreateCommentResultDTO writeComment(CommentRequestDTO.CreateCommentDTO request, String userId);

    public CommentResponseDTO.LikeCommentResultDTO likeComment(CommentRequestDTO.LikeCommentDTO request, String userId);

    public void deleteComment(CommentRequestDTO.DeleteCommentDTO request, String userId);

}
