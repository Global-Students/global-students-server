package com.example.globalStudents.domain.chat.repository;

import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long>, ChatRoomRepositoryCustom {

}
