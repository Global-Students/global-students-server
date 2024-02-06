package com.example.globalStudents.domain.myPage.repository;

import com.example.globalStudents.domain.myPage.entity.UserImageEntity;
import com.example.globalStudents.domain.myPage.enums.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImageEntity, Long> {
    List<UserImageEntity> findByUserIdAndType(Long userId, ImageType type);
}
