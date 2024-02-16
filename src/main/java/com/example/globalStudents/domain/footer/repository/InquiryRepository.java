package com.example.globalStudents.domain.footer.repository;

import com.example.globalStudents.domain.footer.entity.InquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InquiryRepository extends JpaRepository<InquiryEntity,Long> {
    Optional<InquiryEntity> findByType(String type);
}
