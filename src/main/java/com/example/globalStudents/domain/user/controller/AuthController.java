package com.example.globalStudents.domain.user.controller;

import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.service.MailService;
import com.example.globalStudents.domain.user.service.MailServiceImpl;
import com.example.globalStudents.domain.user.service.UserService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/join")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/information")
    public ApiResponse<UserResponseDTO.JoinResultDTO> join(
            @RequestBody
            UserRequestDTO.JoinDTO joinDTO
    ){
        UserResponseDTO.JoinResultDTO user = userService.createUser(joinDTO);
        return ApiResponse.onCreated(user);
    }

    @GetMapping("/check-id/{user_id}")
    public ApiResponse<UserResponseDTO.CheckIdResultDTO> checkId(
            @PathVariable
            String user_id
    ){
        UserResponseDTO.CheckIdResultDTO id = userService.checkId(user_id);
        return ApiResponse.onSuccess(id);
    }

    @GetMapping("/check-nickname/{user_nickname}")
    public ApiResponse<UserResponseDTO.CheckNicknameResultDTO> checkNickname(
            @PathVariable
            String user_nickname
    ){
        UserResponseDTO.CheckNicknameResultDTO nickname = userService.checkNickname(user_nickname);
        return ApiResponse.onCreated(nickname);
    }

}
