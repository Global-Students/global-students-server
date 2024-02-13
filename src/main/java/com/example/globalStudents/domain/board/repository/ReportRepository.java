package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.CommentEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.ReportEntity;
import com.example.globalStudents.domain.board.enums.ReportType;
import com.example.globalStudents.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

    List<ReportEntity> findByReportUserAndComment(UserEntity user, CommentEntity comment);

    List<ReportEntity> findByReportUserAndPost(UserEntity reportUser, PostEntity post);

    int countAllByReportedUser(UserEntity reportedUser);

    @Query("SELECT r FROM ReportEntity r WHERE r.type = :reportType ORDER BY CASE WHEN r.status = 'NOT_RECEIVED' THEN  0 ELSE 1 END")
    List<ReportEntity> findByTypeOrderedByReportStatus(@Param("reportType") ReportType reportType);

    int countAllByPost(PostEntity post);

    int countAllByComment(CommentEntity comment);
}
