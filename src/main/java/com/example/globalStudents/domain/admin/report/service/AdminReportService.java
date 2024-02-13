package com.example.globalStudents.domain.admin.report.service;

import com.example.globalStudents.domain.admin.report.dto.AdminReportResponseDTO;
import com.example.globalStudents.domain.board.dto.PostResponseDTO;

public interface AdminReportService {

    public AdminReportResponseDTO.ReportListResultDTO getAllReports();

    public AdminReportResponseDTO.HandleReportResultDTO handleReport(Long reportId, String handlingType);

    public PostResponseDTO.GetPostResultDTO getPost(Long postId);
}
