package com.example.globalStudents.domain.chat.repository;

import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUserEntity, Long>, ChatRoomUserRepositoryCustom {

}
