package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.dto.ReportRequestDTO;
import com.example.globalStudents.domain.board.entity.ReportEntity;

public interface ReportService {

    public ReportEntity createReport(ReportRequestDTO.CreateReportDTO request);

}
