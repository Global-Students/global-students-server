package com.example.globalStudents.domain.admin.user.service;

import com.example.globalStudents.domain.admin.user.converter.AdminConverter;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService{

    private final AdminConverter userConverter;
    @Override
    public UserResponseDTO.AllUsersResultDTO getAllUsers(String filtering_type) {
        return userConverter.toResponseList(filtering_type);
    }
}
