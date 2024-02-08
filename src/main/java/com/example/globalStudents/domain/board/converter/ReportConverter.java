package com.example.globalStudents.domain.board.converter;

import com.example.globalStudents.domain.board.dto.ReportRequestDTO;
import com.example.globalStudents.domain.board.dto.ReportResponseDTO;
import com.example.globalStudents.domain.board.entity.CommentEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.ReportEntity;
import com.example.globalStudents.domain.board.enums.ReportStatus;
import com.example.globalStudents.domain.board.enums.ReportType;
import com.example.globalStudents.domain.user.entity.UserEntity;

import java.time.LocalDateTime;

public class ReportConverter {

    public static ReportEntity toPostReport(ReportRequestDTO.CreateReportDTO request, UserEntity user, PostEntity post) {
        return ReportEntity.builder()
                .user(user)
                .post(post)
                .type(ReportType.POST)
                .body(request.getBody())
                .status(ReportStatus.NOT_RECEIVED)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ReportEntity toCommentReport(ReportRequestDTO.CreateReportDTO request, UserEntity user, CommentEntity comment) {
        return ReportEntity.builder()
                .user(user)
                .comment(comment)
                .type(ReportType.COMMENT)
                .body(request.getBody())
                .status(ReportStatus.NOT_RECEIVED)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ReportResponseDTO.ReportResultDTO toReportResultDTO(ReportEntity report) {
        return ReportResponseDTO.ReportResultDTO.builder()
                .id(report.getId().toString())
                .build();
    }
}
