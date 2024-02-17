package com.example.globalStudents.domain.friends.controller;

import com.example.globalStudents.domain.friends.dto.FriendsResponseDTO;
import com.example.globalStudents.domain.friends.service.FriendsService;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;
import com.example.globalStudents.domain.user.service.UserService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsController {
    @Autowired
    private final FriendsService friendsService;
    @Autowired
    private final UserService userService;
    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("")
    public ApiResponse<List<FriendsResponseDTO.FriendsDTO>> getFriendsPage() {
        List<FriendsResponseDTO.FriendsDTO> friendsDTO = friendsService.getFriendsPage(getCurrentUserId());
        return ApiResponse.onSuccess(friendsDTO);
    }

    @GetMapping("/{friend_id}")
    public ApiResponse<FriendsResponseDTO.FriendProfileDTO> getFriendProfile(@PathVariable String friend_id) {
        FriendsResponseDTO.FriendProfileDTO friendProfileDTO = friendsService.getFriendProfile(getCurrentUserId(),friend_id);
        return ApiResponse.onSuccess(friendProfileDTO);
    }

}
