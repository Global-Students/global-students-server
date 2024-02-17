package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    BoardEntity findByUniversityIdAndNameContaining(Long universityId, String name);
}
