package com.example.survey.domain.answer.domain;

import com.example.survey.domain.option.domain.Option;
import com.example.survey.domain.question.domain.Question;
import com.example.survey.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "answer")
@NoArgsConstructor
@Getter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    // 답변한 유저의 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 답변이 속한 질문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // 서술형 질문일 경우 답변 내용, 선택형 질문일 경우 NULL
    @Column(name = "answer_text")
    private String answerText;

    // 선택형 질문일 경우 option이 null이거나, 1개 이상
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

}
