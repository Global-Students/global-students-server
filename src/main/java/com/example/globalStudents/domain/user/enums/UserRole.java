package com.example.globalStudents.domain.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {

    USER("사용자"),
    ADMIN("관리자")

    ;

    private final String description;
}
