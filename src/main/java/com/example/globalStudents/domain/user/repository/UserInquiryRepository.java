package com.example.globalStudents.domain.user.repository;

import com.example.globalStudents.domain.user.entity.UserInquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInquiryRepository extends JpaRepository<UserInquiryEntity,Long> {

    @Query("SELECT ui FROM UserInquiryEntity ui ORDER BY CASE WHEN ui.status ='PROCESSING' THEN 0 ELSE 1 END")
    List<UserInquiryEntity> findAllOrderedByStatus();
}
