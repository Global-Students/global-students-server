package com.example.globalStudents.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO{
        Boolean privacy;
        Boolean terms;
        Boolean marketing;

        String userId;
        String password;
        String confirmPassword;
        String firstName;
        String lastName;
        String birthYear;
        String birthMonth;
        String birthDate;
        String nickname;
        String nationality;
        String hostCountry;
        String homeUniversity;
        String hostUniversity;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserIdCheckDTO{
        String userId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserNicknameCheckDTO{
        String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UniversityEmailDTO{
        String email;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UniversityEmailVerificationDTO{
        String code;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UniversityDocumentDTO{
        String fileUrl;
        String fileName;
        String fileSize;
        String contentType;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLoginDTO{
        String username;
        String password;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindPasswordDTO{
        String email;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailCodeVerificationDTO{
        String email;
        String code;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResetPasswordDTO{
        String email;
        String password;
        String confirmPassword;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindIdDTO{
        String email;
    }

}
