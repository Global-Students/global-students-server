package com.example.globalStudents.domain.board.converter;

import com.example.globalStudents.domain.board.dto.PostImageResponseDTO;
import com.example.globalStudents.domain.board.entity.PostImageEntity;

import java.time.LocalDateTime;

public class PostImageConverter {

    public static PostImageEntity toPostImageEntity(String fileName, String url) {
        return PostImageEntity.builder()
                .fileName(fileName)
                .imageUrl(url)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static PostImageResponseDTO.UploadPostImageResultDTO toUploadPostImageResult(PostImageEntity postImage) {
        return PostImageResponseDTO.UploadPostImageResultDTO.builder()
                .imageId(postImage.getId().toString())
                .uploadURL(postImage.getImageUrl())
                .build();
    }
}
