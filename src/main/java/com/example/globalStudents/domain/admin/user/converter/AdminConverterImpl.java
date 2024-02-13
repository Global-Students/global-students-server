package com.example.globalStudents.domain.admin.user.converter;

import com.example.globalStudents.domain.board.repository.ReportRepository;
import com.example.globalStudents.domain.myPage.entity.UserImageEntity;
import com.example.globalStudents.domain.myPage.enums.ImageType;
import com.example.globalStudents.domain.myPage.repository.UserImageRepository;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.enums.UserStatus;
import com.example.globalStudents.domain.user.repository.UserRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminConverterImpl implements AdminConverter {

    private final UserImageRepository userImageRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public UserResponseDTO.UserDetailInfoResultDTO toResponse(UserEntity userEntity) {

        List<UserImageEntity> imageList = userImageRepository.findByUserIdAndType(userEntity.getId(), ImageType.University);
        int countReport = reportRepository.countAllByReportedUser(userEntity);

        String isBanned = UserStatus.REGISTERED.getDescription();
        if(userEntity.getStatus()==UserStatus.BANNED){
            isBanned = UserStatus.BANNED.getDescription();
        }

        return UserResponseDTO.UserDetailInfoResultDTO.builder()
                .uid(userEntity.getUid())
                .userId(userEntity.getUserId())
                .name(userEntity.getFirstName()+" "+userEntity.getLastName())
                .nickname(userEntity.getNickname())
                .birth(userEntity.getBirth().toString())
                .hostCountry(userEntity.getHostCountry().getName())
                .nationality(userEntity.getNationality().getName())
                .hostUniversity(userEntity.getHostUniversity().getName())
                .homeUniversity(userEntity.getHomeUniversity().getName())
                .fileName(imageList.get(imageList.size() - 1).getImageName())
                .fileUrl(imageList.get(imageList.size() - 1).getImageUrl())
                .isVerified(userEntity.getVerification().getDescription())
                .countReport(countReport)
                .isBanned(isBanned)
                .createdAt(userEntity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();

    }

    @Transactional
    public UserResponseDTO.AllUsersResultDTO toResponseList(String filtering_type){
        List<UserEntity> userList;
        if(filtering_type.equals("all")){
            userList = userRepository.findAll();
        } else if(filtering_type.equals("unverified")){
            userList = userRepository.findAllByVerification(UserStatus.UNVERIFIED);
        } else {
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }
        List<UserResponseDTO.UserDetailInfoResultDTO> list = userList.stream()
                .map(this::toResponse)
                .toList();

        return UserResponseDTO.AllUsersResultDTO.builder()
                .userList(list)
                .build();
    }
}
