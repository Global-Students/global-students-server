package com.example.globalStudents.domain.user.service;

import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;

public interface UserService {

//    public UserResponseDTO.JoinResultDTO createUser(UserRequestDTO.JoinDTO joinDTO);

    public UserResponseDTO.CheckIdDTO checkId(String userId);

    public UserResponseDTO.CheckNicknameDTO checkNickname(String nickname);
}
