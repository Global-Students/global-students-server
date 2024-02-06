package com.example.globalStudents.domain.myPage.service;

import com.example.globalStudents.domain.myPage.dto.MypageRequestDTO;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;

public interface MypageService {
    MypageResponseDTO.MypageDTO getMyPage(Long userId, MypageRequestDTO request);
    MypageResponseDTO.MypageInfoDTO getUserInfo(Long userId);
    MypageResponseDTO.MypageProfileDTO getUserProfile(Long userId);
    MypageRequestDTO.MypageInfoUpdateDTO updateUserProfile(Long userId, MypageRequestDTO.MypageInfoUpdateDTO requestDTO);
    MypageRequestDTO.MypageProfileUpdateDTO updateProfilePrivacy(Long userId, MypageRequestDTO.MypageProfileUpdateDTO requestDTO);

}
