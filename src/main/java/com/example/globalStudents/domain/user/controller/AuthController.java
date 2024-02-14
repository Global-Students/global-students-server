package com.example.globalStudents.domain.user.controller;

import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.service.MailService;
import com.example.globalStudents.domain.user.service.MailServiceImpl;
import com.example.globalStudents.domain.user.service.UserService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import com.example.globalStudents.global.util.RedisUtil;
import com.univcert.api.UnivCert;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request,response);
        return ApiResponse.onCreated("LOGOUT");
    }

    @PostMapping("/join/information")
    public ApiResponse<UserResponseDTO.JoinResultDTO> join(
            @RequestPart(name = "join") UserRequestDTO.JoinDTO joinDTO,
            @RequestPart(name = "file") MultipartFile multipartFile
    ){
        UserResponseDTO.JoinResultDTO user = userService.createUser(joinDTO,multipartFile);
        return ApiResponse.onCreated(user);
    }

    @GetMapping("/join/check-id/{user_id}")
    public ApiResponse<UserResponseDTO.CheckIdResultDTO> checkId(
            @PathVariable
            String user_id
    ){
        UserResponseDTO.CheckIdResultDTO id = userService.checkId(user_id);
        return ApiResponse.onSuccess(id);
    }

    @GetMapping("/join/check-nickname/{user_nickname}")
    public ApiResponse<UserResponseDTO.CheckNicknameResultDTO> checkNickname(
            @PathVariable
            String user_nickname
    ){
        UserResponseDTO.CheckNicknameResultDTO nickname = userService.checkNickname(user_nickname);
        return ApiResponse.onSuccess(nickname);
    }

    @PostMapping("/join/university-verification/email")
    public ApiResponse<UserResponseDTO.UniversityEmailResultDTO> sendUniversityMail(
            @RequestBody
            UserRequestDTO.UniversityEmailDTO universityEmailDTO
    ){
        UserResponseDTO.UniversityEmailResultDTO universityEmailResult = userService.certifyUniversity(universityEmailDTO.getEmail(), universityEmailDTO.getUniversity());
        return ApiResponse.onCreated(universityEmailResult);
    }

    @PostMapping("/join/university-verification/email/code")
    public ApiResponse<UserResponseDTO.UniversityEmailVerificationResultDTO> checkUniversityCode(
            @RequestBody
            UserRequestDTO.UniversityEmailVerificationDTO universityEmailVerificationDTO
    ){
        UserResponseDTO.UniversityEmailVerificationResultDTO verificationResult = userService.certifyCode(universityEmailVerificationDTO.getEmail(), universityEmailVerificationDTO.getUniversity(), universityEmailVerificationDTO.getCode());
        return ApiResponse.onCreated(verificationResult);
    }

    @PostMapping("/refresh")
    public ApiResponse<UserResponseDTO.RefreshResultDTO> refresh(
            HttpServletRequest request, HttpServletResponse response
    ){
        UserResponseDTO.RefreshResultDTO refreshResultDTO = userService.refresh(request,response);
        return ApiResponse.onCreated(refreshResultDTO);
    }

}
