package com.example.globalStudents.domain.myPage.service;

import com.example.globalStudents.domain.myPage.dto.UserImageResponseDTO;
import com.example.globalStudents.domain.myPage.enums.ImageType;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageService {
    UserImageResponseDTO.UploadUserImageResultDTO uploadUserImage(MultipartFile multipartFile, ImageType imageType);
}
