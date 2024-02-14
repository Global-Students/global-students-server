package com.example.globalStudents.domain.user.repository;

import com.example.globalStudents.domain.user.entity.CountryEntity;
import com.example.globalStudents.domain.user.entity.InquiryEntity;
import com.example.globalStudents.domain.user.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InquiryRepository extends JpaRepository<InquiryEntity,Long> {
    Optional<InquiryEntity> findByType(String type);
}
