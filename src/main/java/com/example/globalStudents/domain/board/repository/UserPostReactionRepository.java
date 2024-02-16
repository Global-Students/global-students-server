package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.UserPostReactionEntity;
import com.example.globalStudents.domain.board.enums.UserPostReactionType;
import com.example.globalStudents.domain.board.id.UserPostReactionID;
import com.example.globalStudents.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPostReactionRepository extends JpaRepository<UserPostReactionEntity, UserPostReactionID> {
    @Query("SELECT upr FROM UserPostReactionEntity upr WHERE upr.user.userId = :userId AND upr.type = :type")
    Page<UserPostReactionEntity> findByUser_UserIdAndTyp(@Param("userId") String userId, @Param("type") UserPostReactionType type, Pageable pageable);

    List<UserPostReactionEntity> findByPostAndUserAndType(PostEntity post, UserEntity user, UserPostReactionType type);
}
