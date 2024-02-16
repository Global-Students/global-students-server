package com.example.globalStudents.domain.admin.inquiry.service;

import com.example.globalStudents.domain.admin.inquiry.converter.AdminInquiryConverter;
import com.example.globalStudents.domain.admin.inquiry.dto.AdminInquiryResponseDTO;
import com.example.globalStudents.domain.footer.enums.InquiryStatus;
import com.example.globalStudents.domain.user.entity.UserInquiryEntity;
import com.example.globalStudents.domain.user.repository.UserInquiryRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminInquiryServiceImpl implements AdminInquiryService{

    private final UserInquiryRepository userInquiryRepository;

    @Override
    public AdminInquiryResponseDTO.InquiryListResultDTO getInquiryList() {

        List<UserInquiryEntity> userInquiryList = userInquiryRepository.findAllOrderedByStatus();

        List<AdminInquiryResponseDTO.InquiryDTO> inquiryDTOList = userInquiryList.stream()
                .map(
                        userInquiry -> {
                            return AdminInquiryConverter.toInquiryDTO(userInquiry);
                        }
                ).collect(Collectors.toList());

        return AdminInquiryResponseDTO.InquiryListResultDTO.builder()
                .inquiryList(inquiryDTOList)
                .build();
    }

    @Override
    public void handleInquiry(Long inquiryId) {
        UserInquiryEntity userInquiry = userInquiryRepository.findById(inquiryId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus._BAD_REQUEST));

        userInquiry.setStatus(InquiryStatus.ACCEPTED);

        userInquiryRepository.save(userInquiry);
    }
}
