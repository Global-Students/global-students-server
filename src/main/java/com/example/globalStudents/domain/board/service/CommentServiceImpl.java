package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.converter.CommentConverter;
import com.example.globalStudents.domain.board.dto.CommentRequestDTO;
import com.example.globalStudents.domain.board.dto.CommentResponseDTO;
import com.example.globalStudents.domain.board.entity.CommentEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.enums.CommentStatus;
import com.example.globalStudents.domain.board.repository.CommentLikeRepository;
import com.example.globalStudents.domain.board.repository.CommentRepository;
import com.example.globalStudents.domain.board.repository.PostRepository;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.repository.UserRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public CommentResponseDTO.CreateCommentResultDTO writeComment(CommentRequestDTO.CreateCommentDTO request, String userId) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus._BAD_REQUEST));

        PostEntity post = postRepository.findById(Long.parseLong(request.getPostId()))
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus.COMMENT_COMMENT_ID_INVALID));

        CommentEntity newComment = CommentConverter.toComment(request);

        newComment.setUser(user);
        newComment.setPost(post);

        return CommentConverter.toCreateCommentResultDTO(commentRepository.save(newComment));
    }

    @Override
    public CommentResponseDTO.LikeCommentResultDTO likeComment(CommentRequestDTO.LikeCommentDTO request, String userId) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus._BAD_REQUEST));

        CommentEntity comment = commentRepository.findById(Long.parseLong(request.getCommentId()))
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus.COMMENT_COMMENT_ID_INVALID));

        //중복 좋아요 체크
        checkCommentLiked(comment, user);
        //좋아요 증가
        comment.incrementLikes();

        return CommentConverter.toLikeCommentResultDTO(commentLikeRepository.save(CommentConverter.toCommentLike(user, comment)));
    }

    public void checkCommentLiked(CommentEntity comment, UserEntity user) {
        if (!commentLikeRepository.findByCommentAndUser(comment, user).isEmpty()) {
            throw new ExceptionHandler(ErrorStatus.COMMENT_ALREADY_LIKED);
        }
    }

    @Override
    public void deleteComment(CommentRequestDTO.DeleteCommentDTO request, String userId) {
        Long commentId = Long.parseLong(request.getCommentId());

        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.COMMENT_COMMENT_ID_INVALID));

        //작성자 확인
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new ExceptionHandler(ErrorStatus._UNAUTHORIZED);
        }

        comment.setStatus(CommentStatus.DELETED);
        commentRepository.save(comment);
    }
}
