package com.example.globalStudents.domain.myPage.dto;

import com.example.globalStudents.domain.board.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MypageResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageDTO{
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageInfoDTO{
        String userId;
        String password;
        String firstName;
        String lastName;
        String birthYear;
        String birthMonth;
        String birthDate;
        String nickname;
        String nationality;
        String hostCountry;
        String hostUniversity;
        String major;
        String phoneNumber;
        String email;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageProfileDTO{
        String nickname;
        String nationality;
        String firstName;
        String lastName;
        String birthYear;
        String birthMonth;
        String birthDate;
        String hostCountry;
        String hostUniversity;
        String major;
        String introduction;
        String skill;
        Integer profilePhotoId;
        Integer backgroundPhotoId;
    }
}
