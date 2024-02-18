package com.example.globalStudents.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO{
        String userId;
        String nickname;
        String nationality;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckIdResultDTO{
        String userId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckNicknameResultDTO{
        String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindIdResultDTO{
        String email;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindPasswordResultDTO{
        String email;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailCodeVerificationResultDTO{
        Boolean verified;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UniversityEmailResultDTO{
        Boolean complete;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UniversityEmailVerificationResultDTO{
        String university;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDetailInfoResultDTO{
        String uid;
        String userId;
        String name;
        String nickname;
        String birth;
        String hostCountry;
        String nationality;
        String hostUniversity;
        String homeUniversity;
        String fileName;
        String fileUrl;
        String isVerified;
        String isBanned;
        Integer countReport;
        String createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AllUsersResultDTO{
        List<UserDetailInfoResultDTO> userList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO{
        String accessToken;
        String userId;
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
        Date expireAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshResultDTO{
        String accessToken;
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
        Date expireAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BanResultDTO{
        boolean ban; // true는 정지 처리 false는 정지 풀림
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardInformationDTO{
        String boardName_1;
        String boardName_2;
        String boardName_3;
        Long boardId_1;
        Long boardId_2;
        Long boardId_3;
    }


}
