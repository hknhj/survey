package com.example.survey.domain.question.domain;

//import com.example.survey.domain.answer.domain.Answer;
//import com.example.survey.domain.option.domain.Option;
import com.example.survey.domain.survey.domain.Survey;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@NoArgsConstructor
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    // 질문이 속한 설문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    // 질문 내용
    @Column(name = "content", nullable = false)
    private String content;

    // 질문 타입 (서술형, 단일 선택형, 다중 선택형)
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    // 필수 답변 여부
    @Column(name = "is_required")
    private Boolean required = true;

    // 질문 순서
    @Column(name = "order_number")
    private Integer orderNumber;

//    // 질문에 대한 답변들
//    // 답변 없거나 / 서술형 / 단일 선택형 / 다중 선택형
//    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Answer> answers = new ArrayList<>();

    @Builder
    public Question(Survey survey, String content, QuestionType questionType, Boolean required, Integer orderNumber) {
        this.survey = survey;
        this.content = content;
        this.questionType = questionType;
        this.required = required;
        this.orderNumber = orderNumber;
    }

}
