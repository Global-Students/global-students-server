package com.example.globalStudents.domain.myPage.controller;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.myPage.dto.MypageRequestDTO;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;
import com.example.globalStudents.domain.myPage.service.MypageService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    @Autowired
    private MypageService mypageService;

    @GetMapping("/{userId}")
    public ApiResponse<MypageResponseDTO.MypageDTO> getMyPage(@PathVariable Long userId, @RequestBody MypageRequestDTO request) {
        MypageResponseDTO.MypageDTO response = mypageService.getMyPage(userId, request);
        return ApiResponse.onSuccess(response);
    }
    @GetMapping("/info/{userId}")
    public ApiResponse<MypageResponseDTO.MypageInfoDTO> getUserInfo(@PathVariable Long userId) {
        MypageResponseDTO.MypageInfoDTO response = mypageService.getUserInfo(userId);
        return ApiResponse.onSuccess(response);
    }
    @GetMapping("/profile/{userId}")
    public ApiResponse<MypageResponseDTO.MypageProfileDTO> getUserProfile(@PathVariable Long userId) {
        MypageResponseDTO.MypageProfileDTO response = mypageService.getUserProfile(userId);
        return ApiResponse.onSuccess(response);
    }
    @PatchMapping("/info/update/{userId}")
    public ApiResponse<MypageRequestDTO.MypageInfoUpdateDTO> updateUserProfile(@PathVariable Long userId, @RequestBody MypageRequestDTO.MypageInfoUpdateDTO requestDTO) {
        MypageRequestDTO.MypageInfoUpdateDTO response = mypageService.updateUserProfile(userId, requestDTO);
        return ApiResponse.onSuccess(response);
    }
    @PatchMapping("/profile/update/{userId}")
    public ApiResponse<MypageRequestDTO.MypageProfileUpdateDTO> updateProfilePrivacy(@PathVariable Long userId, @RequestBody MypageRequestDTO.MypageProfileUpdateDTO requestDTO) {
        MypageRequestDTO.MypageProfileUpdateDTO response = mypageService.updateProfilePrivacy(userId, requestDTO);
        return ApiResponse.onSuccess(response);
    }
    @GetMapping("/writepost/{userId}")
    public ApiResponse<Page<PostEntity>> getWrittenPostsByUser(@PathVariable Long userId, Pageable pageable) {
        Page<PostEntity> response = mypageService.findPostsByUserId(userId, pageable);
        return ApiResponse.onSuccess(response);
    }
}
