package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findByUserId(Long userId, Pageable pageable);
    List<PostEntity> findFavoritesByUserId(Long userId); // 이 메서드는 사용자가 좋아하는 게시글을 찾기 위한 가상의 메서드입니다. 실제 구현은 사용자의 즐겨찾기 모델에 따라 달라질 수 있습니다.
}
