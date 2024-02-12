package com.example.globalStudents.domain.admin.user.converter;

import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.entity.UserEntity;

public interface AdminConverter{
    public UserResponseDTO.UserDetailInfoResultDTO toResponse(UserEntity userEntity);

    public UserResponseDTO.AllUsersResultDTO toResponseList(String filtering_type);
}
