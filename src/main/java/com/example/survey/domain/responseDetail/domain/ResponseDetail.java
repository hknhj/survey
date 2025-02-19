package com.example.survey.domain.responseDetail.domain;

import com.example.survey.domain.option.domain.Option;
import com.example.survey.domain.question.domain.Question;
import com.example.survey.domain.response.domain.Response;
import jakarta.persistence.*;
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
    @JoinColumn(name = "option_id")
    private Option option;

    // 서술형 답변 (nullable)
    @Column(name = "answer")
    private String answer;
}
