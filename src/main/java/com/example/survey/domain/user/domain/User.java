package com.example.survey.domain.user.domain;

import com.example.survey.domain.survey.domain.Survey;
import com.example.survey.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    // 이메일
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    // 비밀번호
    @Column(name = "password", nullable = false)
    private String password;

    // 유저명
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    // 유저가 생성한 설문조사 리스트
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survey> surveys = new ArrayList<>();

    @Builder
    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
