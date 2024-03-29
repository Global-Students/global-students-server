package com.example.globalStudents.domain.user.entity;

import com.example.globalStudents.domain.footer.entity.InquiryEntity;
import com.example.globalStudents.domain.footer.enums.InquiryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_inquiry")
public class UserInquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="inquiry_id", nullable = false)
    private InquiryEntity inquiry;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
    private InquiryStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
