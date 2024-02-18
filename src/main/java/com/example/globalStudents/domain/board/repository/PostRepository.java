package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.BoardEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findByUser_UserId(String userId, Pageable pageable);
    @Query("SELECT p FROM PostEntity p WHERE p.board = :board AND p.status != 'DELETED'")
    Page<PostEntity> findAllByBoard(BoardEntity board, PageRequest pageRequest);

    @Query("SELECT p FROM PostEntity p WHERE p.board = :board AND p.createdAt >= :minDay AND p.status != 'DELETED'")
    Page<PostEntity> findAllPopularPost(BoardEntity board, LocalDateTime minDay, PageRequest pageRequest);

    @Query("SELECT p FROM PostEntity p WHERE p.board = :board AND (p.title LIKE %:keyword% OR p.body LIKE %:keyword%) AND p.status != 'DELETED'")
    Page<PostEntity> findAllSearch(BoardEntity board, String keyword, PageRequest pageRequest);

    @Query("SELECT p FROM PostEntity p WHERE p.board = :board AND p.status != 'DELETED' ORDER BY p.createdAt DESC")
    PostEntity findByBoardOrderByCreatedAtDesc(BoardEntity board, PageRequest pageRequest);

    @Query("SELECT p FROM PostEntity p WHERE p.board = :board AND p.status != 'DELETED' AND p.createdAt >= :minday ORDER BY p.likes DESC")
    List<PostEntity> findTopPopularPosts(BoardEntity board, LocalDateTime minday, Pageable pageable);
}
