package com.example.globalStudents.domain.admin.user.service;

import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.entity.UserEntity;

import java.util.List;

public interface AdminUserService {
    public UserResponseDTO.AllUsersResultDTO getAllUsers(String filtering_type);

    public void verifyUser(String userId);

    public UserResponseDTO.BanResultDTO banUser(String userId);
}
