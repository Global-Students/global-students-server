package com.example.globalStudents.domain.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {

    REGISTERED("등록"),
    DORMANT("휴면"),
    UNREGISTERED("해지"),
    PUBLIC("공개"),
    PRIVATE("비공개"),
    UNIVERSITY_PUBLIC("학교 공개"),

    ;

    private final String description;
}
