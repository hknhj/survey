package com.example.survey.domain.question.service;

import com.example.survey.domain.option.domain.Choice;
import com.example.survey.domain.option.service.ChoiceService;
import com.example.survey.domain.question.domain.Question;
import com.example.survey.domain.question.dto.QuestionCreateRequest;
import com.example.survey.domain.question.repository.QuestionRepository;
import com.example.survey.domain.survey.domain.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ChoiceService choiceService;


    // 질문 생성 및 저장
    public Question createQuestion(Survey survey, QuestionCreateRequest questionCreateRequest) {

        // Question 엔티티 생성 및 저장
        Question question = questionRepository.save(questionCreateRequest.toEntity(survey));

        // Choice 생성 및 Question에 추가
        if (questionCreateRequest.getOptions() != null) {
            List<Choice> choices = questionCreateRequest.getOptions().stream()
                    .map(optionCreateRequest -> choiceService.createChoice(question, optionCreateRequest))
                    .toList();

            question.addOptions(choices);
        }

        // Question 저장
        return question;
    }
}
