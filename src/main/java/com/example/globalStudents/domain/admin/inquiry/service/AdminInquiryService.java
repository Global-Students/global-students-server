package com.example.globalStudents.domain.admin.inquiry.service;

import com.example.globalStudents.domain.admin.inquiry.dto.AdminInquiryResponseDTO;

public interface AdminInquiryService {

    public AdminInquiryResponseDTO.InquiryListResultDTO getInquiryList();

    public void handleInquiry(Long inquiryId);
}
