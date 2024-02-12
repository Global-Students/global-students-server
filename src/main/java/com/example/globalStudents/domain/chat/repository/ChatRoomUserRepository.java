package com.example.globalStudents.domain.chat.repository;

import com.example.globalStudents.domain.chat.entity.ChatRoomEntity;
import com.example.globalStudents.domain.chat.entity.ChatRoomUserEntity;
import com.example.globalStudents.domain.user.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUserEntity, Long>{
    List<ChatRoomUserEntity> findByUser(UserEntity userId);

    List<ChatRoomUserEntity> findByChatRoom(ChatRoomEntity chatRoomId);

    ChatRoomUserEntity findByChatRoomIdAndUserIdNot(ChatRoomEntity chatRoomId, UserEntity userId);
}
