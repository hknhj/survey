package com.example.survey.domain.option.domain;

import com.example.survey.domain.question.domain.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "option")
@NoArgsConstructor
@Getter
public class Option {

    // 선택형 질문에 대한 선택지
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    // option이 포함된 Question
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // 선택지 내용
    @Column(nullable = false)
    private String text;

    // 선택지 순서
    @Column(name = "order_number")
    private Integer orderNumber;
}
