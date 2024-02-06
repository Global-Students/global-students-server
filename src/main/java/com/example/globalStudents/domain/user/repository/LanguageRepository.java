package com.example.globalStudents.domain.user.repository;

import com.example.globalStudents.domain.user.entity.CountryEntity;
import com.example.globalStudents.domain.user.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<LanguageEntity,Long> {
    Optional<LanguageEntity> findByName(String name);
}
