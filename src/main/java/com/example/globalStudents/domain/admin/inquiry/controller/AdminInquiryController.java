package com.example.globalStudents.domain.admin.inquiry.controller;

import com.example.globalStudents.domain.admin.inquiry.dto.AdminInquiryResponseDTO;
import com.example.globalStudents.domain.admin.inquiry.service.AdminInquiryService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminInquiryController {

    private final AdminInquiryService adminInquiryService;

    @GetMapping("/inquiry")
    public ApiResponse<AdminInquiryResponseDTO.InquiryListResultDTO> getInquiryList() {

        AdminInquiryResponseDTO.InquiryListResultDTO inquiryListResultDTO = adminInquiryService.getInquiryList();

        return ApiResponse.onSuccess(inquiryListResultDTO);
    }

    @GetMapping("/inquiry/handle/{inquiry_id}")
    public ApiResponse<String> handleUserInquiry(@PathVariable("inquiry_id") Long inquiryId) {

        adminInquiryService.handleInquiry(inquiryId);

        return ApiResponse.onSuccess("");
    }
}
