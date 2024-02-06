package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findByUserId(Long userId, Pageable pageable);
}
