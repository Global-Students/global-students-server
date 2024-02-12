package com.example.globalStudents.global.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import com.example.globalStudents.global.enums.S3FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3Util {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;

    public String uploadFile(MultipartFile file, S3FileType type) {
        String filePath = null;
        switch (type) {
            case UNIV_AUTH:
                filePath = "univ_auth/";
                break;
            case POST_IMAGE:
                filePath = "post_image/";
                break;
            case USER_IMAGE:
                filePath = "user_image/";
                break;
        }
        //파일 이름 생성
        String originalFileName = file.getOriginalFilename();
        int fileExtensionIndex = originalFileName.lastIndexOf(".");
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());
        String fileName = filePath + originalFileName.substring(0, fileExtensionIndex) + "_" + now + fileExtension;

        //metadata 설정
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        //파일 업로드
        try(InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new ExceptionHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }

        return fileName;
    }

    public void deleteFile(String fileName) {
        try {
            amazonS3Client.deleteObject(bucketName, fileName);
        } catch (AmazonS3Exception e) {
            throw new ExceptionHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    public String getUrl(String fileName) {
        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

}