package com.example.globalStudents.domain.myPage.service;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.UserPostReactionEntity;
import com.example.globalStudents.domain.myPage.dto.MypageRequestDTO;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MypageService {
    MypageResponseDTO.MypageDTO getMyPage(String userId, MypageRequestDTO request);
    MypageResponseDTO.MypageInfoDTO getUserInfo(String userId);
    MypageResponseDTO.MypageProfileDTO getUserProfile(String userId);
    MypageRequestDTO.MypageInfoUpdateDTO updateUserProfile(String userId, MypageRequestDTO.MypageInfoUpdateDTO requestDTO);
    MypageRequestDTO.MypageProfileUpdateDTO updateProfilePrivacy(String userId, MypageRequestDTO.MypageProfileUpdateDTO requestDTO);
    Page<PostEntity> findPostsByUserId(String userId, Pageable pageable);
    Page<UserPostReactionEntity> findBookmarkedPostsByUserId(String userId, Pageable pageable);
}
