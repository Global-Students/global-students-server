package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.UserPostReactionEntity;
import com.example.globalStudents.domain.board.enums.UserPostReactionType;
import com.example.globalStudents.domain.board.id.UserPostReactionID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPostReactionRepository extends JpaRepository<UserPostReactionEntity, UserPostReactionID> {
    @Query("SELECT upr FROM UserPostReactionEntity upr WHERE upr.id.user.id = :userId AND upr.type = :type")
    Page<UserPostReactionEntity> findByUserIdAndType(@Param("userId") Long userId, @Param("type") UserPostReactionType type, Pageable pageable);
}
