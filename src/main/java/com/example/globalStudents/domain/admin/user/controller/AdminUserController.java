package com.example.globalStudents.domain.admin.user.controller;

import com.example.globalStudents.domain.admin.user.service.AdminUserService;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping("/users")
    public ApiResponse<UserResponseDTO.AllUsersResultDTO> getUsers(
            @RequestParam
            String filtering_type
    ){
        UserResponseDTO.AllUsersResultDTO userList = adminUserService.getAllUsers(filtering_type);

        return ApiResponse.onSuccess(userList);
    }
}
