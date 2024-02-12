package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.converter.ReportConverter;
import com.example.globalStudents.domain.board.dto.ReportRequestDTO;
import com.example.globalStudents.domain.board.entity.CommentEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.ReportEntity;
import com.example.globalStudents.domain.board.repository.CommentRepository;
import com.example.globalStudents.domain.board.repository.PostRepository;
import com.example.globalStudents.domain.board.repository.ReportRepository;
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
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public ReportEntity createReport(ReportRequestDTO.CreateReportDTO request) {
        if (request.getType().equals("POST")) {
            return createPostReport(request);
        }
        else if(request.getType().equals(("COMMENT"))){
            return createCommentReport(request);
        }
        else {
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }
    }


    public ReportEntity createPostReport(ReportRequestDTO.CreateReportDTO request) {

        PostEntity post = postRepository.findById(Long.parseLong(request.getId()))
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.POST_NOT_FOUND));

        //access token으로부터 userId or UserEntity 가져오는 코드 필요
        Long userId = 6L;
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus._UNAUTHORIZED));

        //중복 신고 확인
        checkPostReported(user, post);

        ReportEntity newReport = ReportConverter.toPostReport(request, user, post);

        return reportRepository.save(newReport);
    }


    public ReportEntity createCommentReport(ReportRequestDTO.CreateReportDTO request) {

        CommentEntity comment = commentRepository.findById(Long.parseLong(request.getId()))
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.REPORT_COMMENT_ID_INVALID));

        //access token으로부터 userId or UserEntity 가져오는 코드 필요
        Long userId = 6L;
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus._UNAUTHORIZED));

        //중복 신고 확인
        checkCommentReported(user, comment);

        ReportEntity newReport = ReportConverter.toCommentReport(request, user, comment);

        return reportRepository.save(newReport);
    }

    public void checkCommentReported(UserEntity user, CommentEntity comment) {
        if (!reportRepository.findByUserAndComment(user, comment).isEmpty()) {
            throw new ExceptionHandler(ErrorStatus.REPORT_ALREADY_REPORTED);
        }
    }

    public void checkPostReported(UserEntity user, PostEntity post) {
        if (!reportRepository.findByUserAndPost(user, post).isEmpty()) {
            throw new ExceptionHandler(ErrorStatus.REPORT_ALREADY_REPORTED);
        }
    }


}
