package com.example.globalStudents.domain.user.controller;

import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.service.UserService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board-information")
@RequiredArgsConstructor
public class HeaderController {

    private final UserService userService;

    @GetMapping()
    public ApiResponse<UserResponseDTO.BoardInformationDTO> getBoardInformation () {
        UserResponseDTO.BoardInformationDTO boardInformation = userService.getBoardInformation();
        return ApiResponse.onCreated(boardInformation);
    }
}
