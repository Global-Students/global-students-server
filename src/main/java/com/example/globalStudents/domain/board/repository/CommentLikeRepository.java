package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.CommentEntity;
import com.example.globalStudents.domain.board.entity.CommentLikeEntity;
import com.example.globalStudents.domain.board.id.CommentLikeID;
import com.example.globalStudents.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, CommentLikeID> {

    List<CommentLikeEntity> findByCommentAndUser(CommentEntity comment, UserEntity user);
}
