package com.example.globalStudents.domain.user.repository;

import com.example.globalStudents.domain.user.entity.UserInquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInquiryRepository extends JpaRepository<UserInquiryEntity,Long> {
}
