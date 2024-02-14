package com.example.globalStudents.domain.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {

    ROLE_TEMP("미인증 사용자"),
    ROLE_USER("사용자"),
    ROLE_ADMIN("관리자")

    ;

    private final String description;
}
