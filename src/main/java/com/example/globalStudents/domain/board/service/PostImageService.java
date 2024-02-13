package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.dto.PostImageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface PostImageService {

    public PostImageResponseDTO.UploadPostImageResultDTO uploadPostImage(MultipartFile multipartFile);
}
