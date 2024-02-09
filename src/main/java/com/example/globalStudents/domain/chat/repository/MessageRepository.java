package com.example.globalStudents.domain.chat.repository;

import com.example.globalStudents.domain.chat.entity.ChatRoomUserEntity;
import com.example.globalStudents.domain.chat.entity.MessageEntity;
import com.example.globalStudents.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long>{

    Optional<MessageEntity> findById(Long id);

    Optional<MessageEntity> findByUserId(Long userId);

    List<MessageEntity> findByChatRoomId(Long chatRoomId);

    List<MessageEntity> findByUserIdNotAndReadFalse(Long userId);

    Optional<MessageEntity> findTopByChatRoomIdOrderByCreatedAtDesc(Long chatRoomId);

}
