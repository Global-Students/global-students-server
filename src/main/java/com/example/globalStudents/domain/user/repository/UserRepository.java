package com.example.globalStudents.domain.user.repository;



import com.example.globalStudents.domain.user.entity.CountryEntity;
import com.example.globalStudents.domain.user.entity.UniversityEntity;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity>findByUserId(String userId);
    Optional<UserEntity>findByNickname(String nickname);
    Optional<UserEntity>findByEmail(String email);
    List<UserEntity> findAllByVerification(UserStatus verification);
}
