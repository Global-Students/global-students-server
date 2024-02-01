package com.example.globalStudents.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "university")
public class UniversityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "hostUniversity", cascade = CascadeType.ALL)
    private List<UserEntity> GuestUserList = new ArrayList<>();

    @OneToMany(mappedBy = "homeUniversity", cascade = CascadeType.ALL)
    private List<UserEntity> HomeUserList = new ArrayList<>();
}
