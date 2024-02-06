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

    @PostMapping("/{userId}")
    public ApiResponse<MypageResponseDTO.MypageDTO> getMyPage(@PathVariable Long userId, @RequestBody MypageRequestDTO request) {
        MypageResponseDTO.MypageDTO response = mypageService.getMyPage(userId, request);
        return ApiResponse.onSuccess(response);
    }
}
