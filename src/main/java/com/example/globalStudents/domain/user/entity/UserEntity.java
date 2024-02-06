package com.example.globalStudents.domain.user.entity;

import com.example.globalStudents.domain.board.entity.*;
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

    private String uid;

    private String userId;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDateTime birth;

    private String nickname;

    private String major;

    private String introduction;

    private String skill;

    private String phone;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
    private UserStatus privacy;


    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
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
    @JoinColumn(name="host_country_id")
    private CountryEntity hostCountry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="nationality_id")
    private CountryEntity nationality;

    @ManyToOne
    @JoinColumn(name="language_id")
    private LanguageEntity language;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserImageEntity> userImageEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostEntity> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPostReactionEntity> userPostReactionList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentEntity> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List <CommentLikeEntity> commentLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List <ReportEntity> reportList = new ArrayList<>();
}
