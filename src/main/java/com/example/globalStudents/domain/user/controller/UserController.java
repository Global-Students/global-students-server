package com.example.globalStudents.domain.user.controller;

import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.service.UserService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/find-id")
    public ApiResponse<UserResponseDTO.FindIdResultDTO> findId (
            @RequestBody
            UserRequestDTO.FindIdDTO findIdDTO
    ) {
        UserResponseDTO.FindIdResultDTO findIdResultDTO= userService.findId(findIdDTO.getEmail());
        return ApiResponse.onCreated(findIdResultDTO);
    }

    @PostMapping("/find-password")
    public ApiResponse<UserResponseDTO.FindPasswordResultDTO> findPassword (
            @RequestBody
            UserRequestDTO.FindPasswordDTO findIdDTO
    ) {
        UserResponseDTO.FindPasswordResultDTO findPasswordResultDTO= userService.findPassword(findIdDTO.getEmail());
        return ApiResponse.onCreated(findPasswordResultDTO);
    }

    @PostMapping("/find-password/code")
    public ApiResponse<UserResponseDTO.MailCodeVerificationResultDTO> verifyCode (
            @RequestBody
            UserRequestDTO.MailCodeVerificationDTO verificationDTO
    ) {
        UserResponseDTO.MailCodeVerificationResultDTO verificationResultDTO= userService.verifyEmailCode(verificationDTO.getEmail(),verificationDTO.getCode());
        return ApiResponse.onCreated(verificationResultDTO);
    }

    @PostMapping("/find-password/reset")
    public ApiResponse<String> resetPassword (
            @RequestBody
            UserRequestDTO.ResetPasswordDTO resetPasswordDTO
    ) {
        userService.resetPassword(resetPasswordDTO.getEmail(),resetPasswordDTO.getPassword());
        return ApiResponse.onCreated("");
    }
}
