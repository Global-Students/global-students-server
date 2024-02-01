package com.example.globalStudents.domain.user.entity;

import com.example.globalStudents.domain.myPage.entity.UserImageEntity;
import com.example.globalStudents.domain.user.enums.UserRole;
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
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long uid;

    private String userId;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDateTime birth;

    private String pageAddress;

    private String nickname;

    private String major;

    private String phone;

    private String email;

    private String introduction;

    private UserStatus privacy;

    private UserStatus status;

    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime inactiveAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAgreeEntity> UserAgreeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="host_university_id")
    private UniversityEntity hostUniversity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="home_university_id")
    private UniversityEntity homeUniversity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="nationality_id")
    private CountryEntity nationality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="region_id")
    private RegionEntity region;

    @OneToOne
    @JoinColumn(name="language_id")
    private LanguageEntity language;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserImageEntity> userImageEntityList = new ArrayList<>();
}
