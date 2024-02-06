package com.example.globalStudents.domain.board.dto;

import lombok.Getter;

public class ReportRequestDTO {

    @Getter
    public static class CreateReportDTO {
        String type;
        String id;
        String body;
    }
}
