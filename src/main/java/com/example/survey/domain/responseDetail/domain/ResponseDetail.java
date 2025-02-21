package com.example.survey.domain.responseDetail.domain;

import com.example.survey.domain.option.domain.Choice;
import com.example.survey.domain.question.domain.Question;
import com.example.survey.domain.response.domain.Response;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "response_detail")
@Getter
@NoArgsConstructor
public class ResponseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_detail_id")
    private Long responseDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id", nullable = false)
    private Response response;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // 선택형 답변 (nullable)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice_id")
    private Choice choice;

    // 서술형 답변 (nullable)
    @Column(name = "answer")
    private String answer;

    @Builder
    public ResponseDetail(Response response, Question question, Choice choice, String answer) {
        this.response = response;
        this.question = question;
        this.choice = choice;
        this.answer = answer;
    }
}
