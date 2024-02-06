package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImageEntity, Long> {
}
