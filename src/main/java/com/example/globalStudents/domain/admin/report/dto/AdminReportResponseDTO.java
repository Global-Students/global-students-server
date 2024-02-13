package com.example.globalStudents.domain.admin.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AdminReportResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportListResultDTO {
        private List<PostReportDTO> postReportList;
        private List<CommentReportDTO> commentReportList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostReportDTO {
        private String reportId;
        private String postTitle;
        private String postUrl;
        private String authorId;
        private String reportDate;
        private String postDate;
        private int reportCount;
        private String reportReason;
        private boolean isHandled;
        private String handledType;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentReportDTO {
        private String reportId;
        private String commentContent;
        private String postUrl;
        private String postTitle;
        private String authorId;
        private String reportDate;
        private int reportCount;
        private String reportReason;
        private boolean isHandled;
        private String handledType;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HandleReportResultDTO {
        private String reportId;
    }
}
