package com.example.globalStudents.domain.board.dto;

import com.example.globalStudents.domain.board.entity.ReportEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportResultDTO {
        String id;
    }
}
