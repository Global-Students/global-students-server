package com.example.globalStudents.domain.user.repository;

import com.example.globalStudents.domain.user.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity,Long> {
    Optional<CountryEntity> findByName(String name);
}
