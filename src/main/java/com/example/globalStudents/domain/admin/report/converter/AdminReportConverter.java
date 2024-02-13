package com.example.globalStudents.domain.admin.report.converter;

import com.example.globalStudents.domain.admin.report.dto.AdminReportResponseDTO;
import com.example.globalStudents.domain.board.entity.ReportEntity;
import com.example.globalStudents.domain.board.enums.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReportConverter {

    public static AdminReportResponseDTO.ReportListResultDTO toReportListResultDTO(
            List<AdminReportResponseDTO.PostReportDTO> postReportDTOList,
            List<AdminReportResponseDTO.CommentReportDTO> commentReportDTOList
    ) {

        return AdminReportResponseDTO.ReportListResultDTO.builder()
                .postReportList(postReportDTOList)
                .commentReportList(commentReportDTOList)
                .build();
    }

    public static AdminReportResponseDTO.PostReportDTO toPostReportDTO(ReportEntity report, int reportCount) {
        return AdminReportResponseDTO.PostReportDTO.builder()
                .reportId(report.getId().toString())
                .postTitle(report.getPost().getTitle())
                .postUrl("/admin/posts/" + report.getPost().getId())
                .authorId(report.getPost().getUid())
                .reportDate(report.getPost().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .postDate(report.getPost().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .reportCount(reportCount)
                .reportReason(report.getBody())
                .isHandled((report.getStatus() == ReportStatus.RECEIVED) ? true : false)
                .handledType(report.getHandleType().toString())
                .build();
    }

    public static AdminReportResponseDTO.CommentReportDTO toCommentReportDTO(ReportEntity report, int reportCount) {
        return AdminReportResponseDTO.CommentReportDTO.builder()
                .reportId(report.getId().toString())
                .commentContent(report.getComment().getBody())
                .postUrl("/admin/posts/" + report.getComment().getPost().getId())
                .postTitle(report.getComment().getPost().getTitle())
                .authorId(report.getComment().getUser().getUserId())
                .reportDate(report.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .reportCount(reportCount)
                .reportReason(report.getBody())
                .isHandled((report.getStatus() == ReportStatus.RECEIVED) ? true : false)
                .handledType(report.getHandleType().toString())
                .build();
    }

    public static AdminReportResponseDTO.HandleReportResultDTO toHandleReportResultDTO(ReportEntity report) {
        return AdminReportResponseDTO.HandleReportResultDTO.builder()
                .reportId(report.getId().toString())
                .build();
    }

}
