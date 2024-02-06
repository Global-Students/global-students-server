package com.example.globalStudents.domain.user.service;

import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;

public interface UserService {

    public UserResponseDTO.JoinResultDTO createUser(UserRequestDTO.JoinDTO joinDTO);

    public UserResponseDTO.CheckIdResultDTO checkId(String userId);

    public UserResponseDTO.CheckNicknameResultDTO checkNickname(String nickname);

    public UserResponseDTO.FindIdResultDTO findId(String email);

    public UserResponseDTO.FindPasswordResultDTO findPassword(String email);

    public UserResponseDTO.MailCodeVerificationResultDTO verifyEmailCode(String email, String code);

    public void resetPassword(String email, String password);
}
