package com.example.globalStudents.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "terms")
public class TermsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean required;

    @OneToMany(mappedBy = "terms", cascade = CascadeType.ALL)
    private List<UserAgreeEntity> GuestUserList = new ArrayList<>();
}
