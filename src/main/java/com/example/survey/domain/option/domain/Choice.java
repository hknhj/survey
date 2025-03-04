package com.example.survey.domain.option.domain;

import com.example.survey.domain.question.domain.Question;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "choice")
@NoArgsConstructor
@Getter
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private Long choiceId;

    // option이 포함된 Question
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // 선택지 내용
    @Column(name = "text", nullable = false)
    private String text;

    // 선택지 순서
    @Column(name = "order_number")
    private Integer orderNumber;

    @Builder
    public Choice(Question question, String text, Integer orderNumber) {
        this.question = question;
        this.text = text;
        this.orderNumber = orderNumber;
    }
}
