package com.example.globalStudents.domain.friends.service;

import com.example.globalStudents.domain.friends.dto.FriendsResponseDTO;

import java.util.List;

public interface FriendsService {
    List<FriendsResponseDTO.FriendsDTO> getFriendsPage(String userId);
    FriendsResponseDTO.FriendProfileDTO getFriendProfile(String userId, String friend_id);
}
