package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.converter.PostImageConverter;
import com.example.globalStudents.domain.board.dto.PostImageResponseDTO;
import com.example.globalStudents.domain.board.entity.PostImageEntity;
import com.example.globalStudents.domain.board.repository.PostImageRepository;
import com.example.globalStudents.global.enums.S3FileType;
import com.example.globalStudents.global.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class PostImageServiceImpl implements PostImageService{

    private final PostImageRepository postImageRepository;
    private final S3Util s3Util;

    @Override
    public PostImageResponseDTO.UploadPostImageResultDTO uploadPostImage(MultipartFile multipartFile) {
        String fileName = s3Util.uploadFile(multipartFile, S3FileType.POST_IMAGE);
        String imageUrl = s3Util.getUrl(fileName);

        PostImageEntity postImage = PostImageConverter.toPostImageEntity(fileName, imageUrl);

        return PostImageConverter.toUploadPostImageResult(postImageRepository.save(postImage));
    }
}
