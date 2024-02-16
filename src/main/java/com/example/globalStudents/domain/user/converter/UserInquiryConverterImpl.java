package com.example.globalStudents.domain.user.converter;

import com.example.globalStudents.domain.footer.enums.InquiryStatus;
import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.entity.UserInquiryEntity;
import com.example.globalStudents.domain.footer.repository.InquiryRepository;
import com.example.globalStudents.domain.user.repository.UserRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserInquiryConverterImpl implements UserInquiryConverter<UserInquiryEntity, UserRequestDTO.SendInquiryDTO>{

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;
    @Override
    public UserInquiryEntity toEntity(UserRequestDTO.SendInquiryDTO dto, Long userId) {

        var inquiryEntity = inquiryRepository.findByType(dto.getType());
        var userEntity = userRepository.findById(userId);

        if(inquiryEntity.isEmpty() || userEntity.isEmpty()) throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);


        return UserInquiryEntity.builder()
                .inquiry(inquiryEntity.get())
                .user(userEntity.get())
                .body(dto.getBody())
                .status(InquiryStatus.PROCESSING)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
