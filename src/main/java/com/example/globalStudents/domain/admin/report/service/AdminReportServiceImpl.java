package com.example.globalStudents.domain.admin.report.service;

import com.example.globalStudents.domain.admin.report.converter.AdminReportConverter;
import com.example.globalStudents.domain.admin.report.dto.AdminReportResponseDTO;
import com.example.globalStudents.domain.board.converter.PostConverter;
import com.example.globalStudents.domain.board.dto.PostResponseDTO;
import com.example.globalStudents.domain.board.entity.CommentEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.ReportEntity;
import com.example.globalStudents.domain.board.enums.*;
import com.example.globalStudents.domain.board.repository.CommentRepository;
import com.example.globalStudents.domain.board.repository.PostRepository;
import com.example.globalStudents.domain.board.repository.ReportRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminReportServiceImpl implements AdminReportService{

    private final ReportRepository reportRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public AdminReportResponseDTO.ReportListResultDTO getAllReports() {

        List<ReportEntity> postReportList = reportRepository.findByTypeOrderedByReportStatus(ReportType.POST);
        List<ReportEntity> commentReportList = reportRepository.findByTypeOrderedByReportStatus(ReportType.COMMENT);

        List<AdminReportResponseDTO.PostReportDTO> postReportDTOList = postReportList.stream()
                .map(
                        report -> {
                            return AdminReportConverter.toPostReportDTO(report, reportRepository.countAllByPost(report.getPost()));
                        }
                ).collect(Collectors.toList());

        List<AdminReportResponseDTO.CommentReportDTO> commentReportDTOList = commentReportList.stream()
                .map(
                        report -> {
                            return AdminReportConverter.toCommentReportDTO(report, reportRepository.countAllByComment(report.getComment()));
                        }
                ).collect(Collectors.toList());

        return AdminReportConverter.toReportListResultDTO(postReportDTOList, commentReportDTOList);
    }

    @Override
    public AdminReportResponseDTO.HandleReportResultDTO handleReport(Long reportId, String handlingType) {
        ReportEntity report = reportRepository.findById(reportId)
                .orElseThrow(()->new ExceptionHandler(ErrorStatus._BAD_REQUEST));

        //중복 처리 확인
        if (report.getStatus() == ReportStatus.RECEIVED) {
            throw new ExceptionHandler(ErrorStatus.ADMIN_ALREADY_HANDLED_REPORT);
        }

        if (handlingType.equals("delete")) {
            //게시글 DELETED status로 변경
            if (report.getType() == ReportType.POST) {
                PostEntity post = report.getPost();
                post.setStatus(PostStatus.DELETED);
                post.setDeletedAt(LocalDateTime.now());
                postRepository.save(post);
            }
            //댓글 DELETED status로 변경
            else if (report.getType() == ReportType.COMMENT) {
                CommentEntity comment = report.getComment();
                comment.setStatus(CommentStatus.DELETED);
                comment.setDeletedAt(LocalDateTime.now());
                commentRepository.save(comment);
            }
            else {
                throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
            }

            report.setHandleType(ReportHandleType.DELETE);

        } else if (handlingType.equals("maintain")) {
            report.setHandleType(ReportHandleType.MAINTAIN);
        }
        else {
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }

        report.setStatus(ReportStatus.RECEIVED);

        return AdminReportConverter.toHandleReportResultDTO(reportRepository.save(report));
    }

    @Override
    public PostResponseDTO.GetPostResultDTO getPost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus.POST_NOT_FOUND));

        return PostConverter.toAdminPostResult(post);
    }

}
