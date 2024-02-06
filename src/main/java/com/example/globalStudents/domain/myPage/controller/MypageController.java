package com.example.globalStudents.domain.myPage.controller;

import com.example.globalStudents.domain.myPage.dto.MypageRequestDTO;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;
import com.example.globalStudents.domain.myPage.service.MypageService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
}
