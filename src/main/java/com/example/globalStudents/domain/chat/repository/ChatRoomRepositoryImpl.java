package com.example.globalStudents.domain.chat.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom{
    private final JPAQueryFactory queryFactory;
}