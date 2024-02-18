package com.example.globalStudents.domain.friends.repository;

import com.example.globalStudents.domain.friends.entity.FriendEntity;
import com.example.globalStudents.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    List<FriendEntity> findByFromUser(UserEntity fromUser);
}
