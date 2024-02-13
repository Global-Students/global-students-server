package com.example.globalStudents.domain.admin.user.controller;

import com.example.globalStudents.domain.admin.user.service.AdminUserService;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping("/users/{user_id}/verification")
    public ApiResponse<String> verifyUser(
            @PathVariable
            String user_id
    ){
        adminUserService.verifyUser(user_id);

        return ApiResponse.onSuccess(" ");
    }

    @PostMapping("/users/{user_id}/ban")
    public ApiResponse<UserResponseDTO.BanResultDTO> banUser(
            @PathVariable
            String user_id
    ){
        UserResponseDTO.BanResultDTO banResultDTO = adminUserService.banUser(user_id);

        return ApiResponse.onSuccess(banResultDTO);
    }
}
