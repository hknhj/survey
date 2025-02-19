package com.example.survey.domain.survey.dto;

import com.example.survey.domain.question.dto.QuestionCreateRequest;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SurveyCreateRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<QuestionCreateRequest> questions;
}
