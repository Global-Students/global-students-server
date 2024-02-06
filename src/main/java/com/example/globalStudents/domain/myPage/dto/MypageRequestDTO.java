package com.example.globalStudents.domain.myPage.dto;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MypageRequestDTO {
    private Long userId;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageInfoUpdateDTO{
        private String password;
        private String birth;
        private String nickname;
        private String phone;
        private String email;
        private UserStatus phonePrivacy;
        private UserStatus emailPrivacy;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageProfileUpdateDTO {
        private UserStatus namePrivacy;
        private UserStatus birthPrivacy;
        private UserStatus universityPrivacy;
        private UserStatus majorPrivacy;
    }
}