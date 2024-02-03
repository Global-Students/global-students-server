package com.example.globalStudents.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_agree")
public class UserAgreeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="terms_id")
    private TermsEntity terms;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean agree;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
