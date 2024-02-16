package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.dto.ReportRequestDTO;
import com.example.globalStudents.domain.board.dto.ReportResponseDTO;

public interface ReportService {

    public ReportResponseDTO.ReportResultDTO createReport(ReportRequestDTO.CreateReportDTO request, String userId);

}
