package com.example.globalStudents.domain.chat.repository;

import com.example.globalStudents.domain.chat.entity.ChatRoomUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUserEntity, Long>, ChatRoomUserRepositoryCustom {

}
