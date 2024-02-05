package com.example.globalStudents.domain.board.repository;

import com.example.globalStudents.domain.board.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
}
