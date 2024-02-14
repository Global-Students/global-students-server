package com.example.globalStudents.domain.myPage.dto;

import com.example.globalStudents.domain.myPage.enums.ImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserImageResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadUserImageResultDTO {
        private String imageId;
        private String uploadURL;
        private ImageType imageType;
    }
}
