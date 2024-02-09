package com.example.globalStudents.domain.user.service;

import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface UserService {

    public UserResponseDTO.JoinResultDTO createUser(UserRequestDTO.JoinDTO joinDTO);

    public UserResponseDTO.CheckIdResultDTO checkId(String userId);

    public UserResponseDTO.CheckNicknameResultDTO checkNickname(String nickname);

    public UserResponseDTO.FindIdResultDTO findId(String email);

    public UserResponseDTO.FindPasswordResultDTO findPassword(String email);

    public UserResponseDTO.MailCodeVerificationResultDTO verifyEmailCode(String email, String code);

    public void resetPassword(String email, String password);

    public UserResponseDTO.UniversityEmailResultDTO certifyUniversity(String email, String university);

    public UserResponseDTO.UniversityEmailVerificationResultDTO certifyCode(String email, String university, String code);

    public void logout (HttpServletRequest request);
}
