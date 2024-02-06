package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.CommentLikeEntity;
import com.example.globalStudents.domain.board.id.CommentLikeID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, CommentLikeID> {
}
