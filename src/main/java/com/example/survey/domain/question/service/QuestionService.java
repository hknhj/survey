package com.example.survey.domain.question.service;

import com.example.survey.domain.question.domain.Question;
import com.example.survey.domain.question.domain.QuestionType;
import com.example.survey.domain.question.dto.QuestionCreateRequest;
import com.example.survey.domain.question.repository.QuestionRepository;
import com.example.survey.domain.survey.domain.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 질문 생성 및 저장
    public Question createQuestion(Survey survey, QuestionCreateRequest questionCreateRequest) {

        // Question 엔티티 생성 및 저장
        return questionRepository.save(Question.builder()
                .survey(survey)
                .content(questionCreateRequest.getContent())
                .questionType(QuestionType.fromString(questionCreateRequest.getQuestionType()))
                .required(questionCreateRequest.getRequired())
                .orderNumber(questionCreateRequest.getOrderNumber())
                .build());
    }
}
