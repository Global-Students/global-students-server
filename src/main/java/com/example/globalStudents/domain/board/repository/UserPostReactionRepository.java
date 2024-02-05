package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.UserPostReactionEntity;
import com.example.globalStudents.domain.board.id.UserPostReactionID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostReactionRepository extends JpaRepository<UserPostReactionEntity, UserPostReactionID> {
}
