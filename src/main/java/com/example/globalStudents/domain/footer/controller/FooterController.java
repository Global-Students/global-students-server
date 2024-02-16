package com.example.globalStudents.domain.footer.controller;

import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.repository.UserRepository;
import com.example.globalStudents.domain.user.service.UserService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import com.example.globalStudents.global.auth.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/footer")
@RequiredArgsConstructor
public class FooterController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/inquiry")
    public ApiResponse<String> sendInquiry (
            @RequestBody
            UserRequestDTO.SendInquiryDTO sendInquiryDTO
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var entity = userRepository.findByUserId(auth.getName());
        if (entity.isPresent()) {
            userService.sendInquiry(entity.get().getId(), sendInquiryDTO);
        } else {
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }
        return ApiResponse.onCreated("");
    }
}
