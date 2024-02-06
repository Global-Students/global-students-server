package com.example.globalStudents.domain.user.repository;

import com.example.globalStudents.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
