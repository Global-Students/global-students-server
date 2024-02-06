package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
