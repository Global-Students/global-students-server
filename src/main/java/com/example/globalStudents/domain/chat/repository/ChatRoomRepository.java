package com.example.globalStudents.domain.chat.repository;

import com.example.globalStudents.domain.chat.entity.ChatRoomEntity;
import com.example.globalStudents.domain.user.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long>{
    Optional<ChatRoomEntity> findById(Long Id);

}
