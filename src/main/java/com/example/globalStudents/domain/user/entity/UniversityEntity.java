package com.example.globalStudents.domain.user.entity;

import com.example.globalStudents.domain.board.entity.BoardEntity;
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

    private String koreanName;

    @OneToMany(mappedBy = "hostUniversity", cascade = CascadeType.ALL)
    private List<UserEntity> GuestUserList = new ArrayList<>();

    @OneToMany(mappedBy = "homeUniversity", cascade = CascadeType.ALL)
    private List<UserEntity> HomeUserList = new ArrayList<>();

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<BoardEntity> UniversityBoardList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="region_id")
    private RegionEntity region;
}
