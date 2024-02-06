package com.example.globalStudents.domain.user.repository;

import com.example.globalStudents.domain.user.entity.TermsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermsRepository extends JpaRepository<TermsEntity,Long> {
    Optional<TermsEntity>findByName(String name);
}
