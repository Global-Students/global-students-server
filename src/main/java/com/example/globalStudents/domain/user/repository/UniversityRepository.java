package com.example.globalStudents.domain.user.repository;

import com.example.globalStudents.domain.user.entity.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversityRepository extends JpaRepository<UniversityEntity,Long> {
    Optional<UniversityEntity>findByName(String name);
}
