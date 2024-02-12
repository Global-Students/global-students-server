package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.CommentEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.ReportEntity;
import com.example.globalStudents.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

    List<ReportEntity> findByUserAndComment(UserEntity user, CommentEntity comment);

    List<ReportEntity> findByUserAndPost(UserEntity user, PostEntity post);

    int countAllByReportedUser(UserEntity reportedUser);
}
