package com.example.globalStudents.domain.myPage.service;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.myPage.dto.MypageRequestDTO;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MypageService {
    MypageResponseDTO.MypageDTO getMyPage(Long userId, MypageRequestDTO request);
    MypageResponseDTO.MypageInfoDTO getUserInfo(Long userId);
    MypageResponseDTO.MypageProfileDTO getUserProfile(Long userId);
    MypageRequestDTO.MypageInfoUpdateDTO updateUserProfile(Long userId, MypageRequestDTO.MypageInfoUpdateDTO requestDTO);
    MypageRequestDTO.MypageProfileUpdateDTO updateProfilePrivacy(Long userId, MypageRequestDTO.MypageProfileUpdateDTO requestDTO);
    Page<PostEntity> findPostsByUserId(Long userId, Pageable pageable);
}
