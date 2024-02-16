package com.example.globalStudents.domain.admin.inquiry.converter;

import com.example.globalStudents.domain.admin.inquiry.dto.AdminInquiryResponseDTO;
import com.example.globalStudents.domain.footer.enums.InquiryStatus;
import com.example.globalStudents.domain.user.entity.UserInquiryEntity;

import java.time.format.DateTimeFormatter;

public class AdminInquiryConverter {

    public static AdminInquiryResponseDTO.InquiryDTO toInquiryDTO(UserInquiryEntity userInquiry) {
        return AdminInquiryResponseDTO.InquiryDTO.builder()
                .inquiryId(userInquiry.getId().toString())
                .userId(userInquiry.getUser().getUserId())
                .content(userInquiry.getBody())
                .createdAt(userInquiry.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .category(userInquiry.getInquiry().getType())
                .isAccepted((userInquiry.getStatus().equals(InquiryStatus.ACCEPTED)) ? true : false)
                .build();
    }

}
