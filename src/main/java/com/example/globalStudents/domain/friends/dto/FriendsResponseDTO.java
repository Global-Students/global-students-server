package com.example.globalStudents.domain.friends.dto;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FriendsResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendsDTO{
        private String nickname;
        private String nationality;
        private String major;
        private String introduction;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendProfileDTO{
        String nickname;
        String introduction;
        String host_university;
        String nationality;
        String major;
        List<PostEntity> writtenPostList;
        List<PostEntity> favoritePostList;
        Long profilePhotoId;
        Long backgroundPhotoId;
    }
}
