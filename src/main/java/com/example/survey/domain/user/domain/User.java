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

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survey> surveys = new ArrayList<>();

    @Builder
    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
