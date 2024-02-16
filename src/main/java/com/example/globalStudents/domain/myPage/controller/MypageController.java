package com.example.globalStudents.domain.myPage.controller;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.UserPostReactionEntity;
import com.example.globalStudents.domain.myPage.dto.MypageRequestDTO;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;
import com.example.globalStudents.domain.myPage.dto.UserImageResponseDTO;
import com.example.globalStudents.domain.myPage.enums.ImageType;
import com.example.globalStudents.domain.myPage.service.MypageService;
import com.example.globalStudents.domain.myPage.service.UserImageService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    @Autowired
    private final MypageService mypageService;
    @Autowired
    private final UserImageService userImageService;

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("")
    public ApiResponse<MypageResponseDTO.MypageDTO> getMyPage(MypageRequestDTO request) {
        MypageResponseDTO.MypageDTO myPageDTO = mypageService.getMyPage(getCurrentUserId(), request);
        return ApiResponse.onSuccess(myPageDTO);
    }

    @GetMapping("/info")
    public ApiResponse<MypageResponseDTO.MypageInfoDTO> getUserInfo() {
        MypageResponseDTO.MypageInfoDTO userInfoDTO = mypageService.getUserInfo(getCurrentUserId());
        return ApiResponse.onSuccess(userInfoDTO);
    }

    @GetMapping("/profile")
    public ApiResponse<MypageResponseDTO.MypageProfileDTO> getUserProfile() {
        MypageResponseDTO.MypageProfileDTO userProfileDTO = mypageService.getUserProfile(getCurrentUserId());
        return ApiResponse.onSuccess(userProfileDTO);
    }

    @PatchMapping("/info/update")
    public ApiResponse<MypageRequestDTO.MypageInfoUpdateDTO> updateUserProfile(@RequestBody MypageRequestDTO.MypageInfoUpdateDTO requestDTO) {
        MypageRequestDTO.MypageInfoUpdateDTO updatedUserInfoDTO = mypageService.updateUserProfile(getCurrentUserId(), requestDTO);
        return ApiResponse.onSuccess(updatedUserInfoDTO);
    }

    @PatchMapping("/profile/update")
    public ApiResponse<MypageRequestDTO.MypageProfileUpdateDTO> updateProfilePrivacy(@RequestBody MypageRequestDTO.MypageProfileUpdateDTO requestDTO) {
        MypageRequestDTO.MypageProfileUpdateDTO updatedProfilePrivacyDTO = mypageService.updateProfilePrivacy(getCurrentUserId(), requestDTO);
        return ApiResponse.onSuccess(updatedProfilePrivacyDTO);
    }

    @GetMapping("/writepost")
    public ApiResponse<Page<PostEntity>> getWrittenPostsByUser(Pageable pageable) {
        Page<PostEntity> writtenPostsPage = mypageService.findPostsByUserId(getCurrentUserId(), pageable);
        return ApiResponse.onSuccess(writtenPostsPage);
    }

    @GetMapping("/favoritepost")
    public ApiResponse<Page<UserPostReactionEntity>> getFavoritePostsByUser(Pageable pageable) {
        Page<UserPostReactionEntity> favoritePostsPage = mypageService.findBookmarkedPostsByUserId(getCurrentUserId(), pageable);
        return ApiResponse.onSuccess(favoritePostsPage);
    }

    @PostMapping("/upload-image")
    public ApiResponse<UserImageResponseDTO.UploadUserImageResultDTO> uploadUserImage(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("imageType") ImageType imageType) {
        UserImageResponseDTO.UploadUserImageResultDTO uploadImageResultDTO = userImageService.uploadUserImage(multipartFile, imageType);
        return ApiResponse.onSuccess(uploadImageResultDTO);
    }
}
