package com.example.globalStudents.domain.myPage.service;

import com.example.globalStudents.domain.myPage.dto.UserImageResponseDTO;
import com.example.globalStudents.domain.myPage.entity.UserImageEntity;
import com.example.globalStudents.domain.myPage.enums.ImageType;
import com.example.globalStudents.domain.myPage.repository.UserImageRepository;
import com.example.globalStudents.global.enums.S3FileType;
import com.example.globalStudents.global.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class UserImageServiceImpl implements UserImageService{

    private final UserImageRepository userImageRepository;
    private final S3Util s3Util;

    @Override
    public UserImageResponseDTO.UploadUserImageResultDTO uploadUserImage(MultipartFile multipartFile, ImageType imageType, Long userId) {
        String fileName = s3Util.uploadFile(multipartFile, S3FileType.USER_IMAGE);
        String imageUrl = s3Util.getUrl(fileName);

        UserImageEntity userImage = UserImageEntity.builder()
                .imageName(fileName)
                .imageUrl(imageUrl)
                .type(imageType)
                .build();

        userImage = userImageRepository.save(userImage);

        return UserImageResponseDTO.UploadUserImageResultDTO.builder()
                .imageId(userImage.getId().toString())
                .uploadURL(userImage.getImageUrl())
                .imageType(userImage.getType())
                .build();
    }
}
