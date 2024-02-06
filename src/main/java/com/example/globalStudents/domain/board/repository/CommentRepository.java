package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
