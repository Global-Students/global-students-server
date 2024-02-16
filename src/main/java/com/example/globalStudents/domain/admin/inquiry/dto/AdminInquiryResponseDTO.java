package com.example.globalStudents.domain.admin.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AdminInquiryResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryListResultDTO {
        private List<InquiryDTO>  inquiryList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryDTO {
        private String inquiryId;
        private String userId;
        private String content;
        private String createdAt;
        private String category;
        private boolean isAccepted;
    }

}
