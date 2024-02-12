package com.example.globalStudents.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatus {

    REGISTERED("등록"),
    BANNED("정지"),
    UNREGISTERED("해지"),
    PUBLIC("공개"),
    PRIVATE("비공개"),
    UNIVERSITY_PUBLIC("학교 공개"),

    VERIFIED("인증"),

    UNVERIFIED("미인증"),

    ;

    private final String description;
}
