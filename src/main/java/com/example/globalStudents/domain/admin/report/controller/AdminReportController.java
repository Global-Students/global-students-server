package com.example.globalStudents.domain.admin.report.controller;

import com.example.globalStudents.domain.admin.report.dto.AdminReportResponseDTO;
import com.example.globalStudents.domain.admin.report.service.AdminReportService;
import com.example.globalStudents.domain.board.dto.PostResponseDTO;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminReportController {


    private final AdminReportService adminReportService;

    @GetMapping("/report")
    public ApiResponse<AdminReportResponseDTO.ReportListResultDTO> getReportList() {

        AdminReportResponseDTO.ReportListResultDTO reportList = adminReportService.getAllReports();

        return ApiResponse.onSuccess(reportList);
    }

    @GetMapping("/report/{report_id}/handle")
    public ApiResponse<AdminReportResponseDTO.HandleReportResultDTO> handleReport(
            @PathVariable("report_id") Long reportId,
            @RequestParam(name = "action") String handlingType) {

        AdminReportResponseDTO.HandleReportResultDTO handleResult = adminReportService.handleReport(reportId, handlingType);

        return ApiResponse.onSuccess(handleResult);
    }

    @GetMapping("/posts/{post_id}")
    public ApiResponse<PostResponseDTO.GetPostResultDTO> getPost(
            @PathVariable("post_id") Long postId
    ) {
        PostResponseDTO.GetPostResultDTO getAdminPostResultDTO = adminReportService.getPost(postId);

        return ApiResponse.onSuccess(getAdminPostResultDTO);
    }
}
