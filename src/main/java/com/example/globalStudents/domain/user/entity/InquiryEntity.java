package com.example.globalStudents.domain.user.entity;

import com.example.globalStudents.domain.board.entity.*;
import com.example.globalStudents.domain.myPage.entity.UserImageEntity;
import com.example.globalStudents.domain.user.entity.TermsEntity;
import com.example.globalStudents.domain.user.entity.UniversityEntity;
import com.example.globalStudents.domain.user.entity.UserAgreeEntity;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inquiry")
public class InquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL)
    private List<UserInquiryEntity> userInquiryList = new ArrayList<>();

}
