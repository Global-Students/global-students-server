package com.example.globalStudents.domain.chat.repository;

import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long>, MessageRepositoryCustom {

}
