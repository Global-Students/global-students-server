package com.example.globalStudents.domain.myPage.service;

import com.example.globalStudents.domain.myPage.dto.MypageRequestDTO;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;

public interface MypageService {
    MypageResponseDTO.MypageDTO getMyPage(Long userId, MypageRequestDTO request);
}