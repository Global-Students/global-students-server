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
import org.springframework.security.core.userdetails.UserDetails;
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

    private Long getCurrentUId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
        return mypageService.findUIdByUserId(userId);
    }

    @GetMapping("/mypage")
    public ApiResponse<MypageResponseDTO.MypageDTO> getMyPage(MypageRequestDTO request) {
        Long userId = getCurrentUId();
        MypageResponseDTO.MypageDTO response = mypageService.getMyPage(userId, request);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/info")
    public ApiResponse<MypageResponseDTO.MypageInfoDTO> getUserInfo() {
        Long userId = getCurrentUId();
        MypageResponseDTO.MypageInfoDTO response = mypageService.getUserInfo(userId);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/profile")
    public ApiResponse<MypageResponseDTO.MypageProfileDTO> getUserProfile() {
        Long userId = getCurrentUId();
        MypageResponseDTO.MypageProfileDTO response = mypageService.getUserProfile(userId);
        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/info/update")
    public ApiResponse<MypageRequestDTO.MypageInfoUpdateDTO> updateUserProfile(@RequestBody MypageRequestDTO.MypageInfoUpdateDTO requestDTO) {
        Long userId = getCurrentUId();
        MypageRequestDTO.MypageInfoUpdateDTO response = mypageService.updateUserProfile(userId, requestDTO);
        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/profile/update")
    public ApiResponse<MypageRequestDTO.MypageProfileUpdateDTO> updateProfilePrivacy(@RequestBody MypageRequestDTO.MypageProfileUpdateDTO requestDTO) {
        Long userId = getCurrentUId();
        MypageRequestDTO.MypageProfileUpdateDTO response = mypageService.updateProfilePrivacy(userId, requestDTO);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/writepost")
    public ApiResponse<Page<PostEntity>> getWrittenPostsByUser(Pageable pageable) {
        Long userId = getCurrentUId();
        Page<PostEntity> response = mypageService.findPostsByUserId(userId, pageable);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/favoritepost")
    public ApiResponse<Page<UserPostReactionEntity>> getFavoritePostsByUser(Pageable pageable) {
        Long userId = getCurrentUId();
        Page<UserPostReactionEntity> likes = mypageService.findBookmarkedPostsByUserId(userId, pageable);
        return ApiResponse.onSuccess(likes);
    }
    @PostMapping("/upload-image")
    public ApiResponse<UserImageResponseDTO.UploadUserImageResultDTO> uploadUserImage(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("imageType") ImageType imageType) {
        Long userId = getCurrentUId();
        UserImageResponseDTO.UploadUserImageResultDTO resultDTO = userImageService.uploadUserImage(multipartFile, imageType, userId);

        return ApiResponse.onSuccess(resultDTO);
    }
}
