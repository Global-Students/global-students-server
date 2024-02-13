package com.example.globalStudents.domain.admin.user.service;

import com.example.globalStudents.domain.admin.user.converter.AdminConverter;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.enums.UserStatus;
import com.example.globalStudents.domain.user.repository.UserRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService{

    private final AdminConverter userConverter;
    private final UserRepository userRepository;
    @Override
    public UserResponseDTO.AllUsersResultDTO getAllUsers(String filtering_type) {
        return userConverter.toResponseList(filtering_type);
    }

    @Override
    @Transactional
    public void verifyUser(String userId) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);
        if(userEntity.isPresent()){
            userEntity.get().setVerification(UserStatus.VERIFIED);
        } else{
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public UserResponseDTO.BanResultDTO banUser(String userId) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);
        if(userEntity.isPresent()){
            if(userEntity.get().getStatus()!=UserStatus.BANNED){
                userEntity.get().setStatus(UserStatus.BANNED);
                return UserResponseDTO.BanResultDTO.builder()
                        .ban(true)
                        .build();
            } else {
                userEntity.get().setStatus(UserStatus.REGISTERED);
                return UserResponseDTO.BanResultDTO.builder()
                        .ban(false)
                        .build();
            }
        } else{
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }
    }
}
