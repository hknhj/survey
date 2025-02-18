package com.example.survey.domain.survey.domain;

//import com.example.survey.domain.question.domain.Question;
import com.example.survey.domain.user.domain.User;
import com.example.survey.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "survey")
@NoArgsConstructor
@Getter
public class Survey extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long surveyId;

    // 설문 조사 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 설문 조사 제목
    @Column(name = "title", nullable = false)
    private String title;

    // 설문 조사 설명
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // 설문 조사 시작일
    @Column(name = "start_date", columnDefinition = "DATETIME")
    private LocalDate startDate;

    // 설문 조사 마감일
    @Column(name = "end_date", columnDefinition = "DATETIME")
    private LocalDate endDate;

    // 설문에 포함된 질문 리스트
//    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Question> questions = new ArrayList<>();

    public void updateSurvey(String title, String description, LocalDate startDate, LocalDate endDate) {
        if (title != null) this.title = title;
        if (description != null) this.description = description;
        if (startDate != null) this.startDate = startDate;
        if (endDate != null) this.endDate = endDate;
    }

    @Builder
    public Survey(User user, String title, String description, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}