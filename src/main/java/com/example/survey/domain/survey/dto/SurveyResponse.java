package com.example.survey.domain.survey.dto;

import com.example.survey.domain.question.dto.QuestionResponse;
import com.example.survey.domain.survey.domain.Survey;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class SurveyResponse {
    private Long surveyId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<QuestionResponse> questions;
    private Integer questionCount;

    // Survey -> SurveyResponse 변환
    public static SurveyResponse from(Survey survey) {
        return SurveyResponse.builder()
                .surveyId(survey.getSurveyId())
                .title(survey.getTitle())
                .description(survey.getDescription())
                .startDate(survey.getStartDate())
                .endDate(survey.getEndDate())
                .questions(survey.getQuestions().stream()
                        .map(QuestionResponse::from)
                        .collect(Collectors.toList()))
                .questionCount(survey.getQuestions().size())
                .build();
    }
}
