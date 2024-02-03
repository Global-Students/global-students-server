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
@Table(name = "country")
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "nationality", cascade = CascadeType.ALL)
    private List<UserEntity> CountryUserList = new ArrayList<>();

    @OneToMany(mappedBy = "hostCountry", cascade = CascadeType.ALL)
    private List<UserEntity> HostCountryUserList = new ArrayList<>();

}
