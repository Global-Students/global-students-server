package com.example.globalStudents.domain.user.repository;

import com.example.globalStudents.domain.board.entity.BoardEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.user.entity.UniversityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<UniversityEntity,Long> {
    Optional<UniversityEntity>findByName(String name);

    @Query("SELECT p FROM UniversityEntity p  WHERE p.name LIKE %:keyword% ")
    List<UniversityEntity> findAllSearch(String keyword);

}
