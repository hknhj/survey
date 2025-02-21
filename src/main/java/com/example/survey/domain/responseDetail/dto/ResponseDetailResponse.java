package com.example.survey.domain.responseDetail.dto;

import com.example.survey.domain.responseDetail.domain.ResponseDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDetailResponse {
    private Long questionId;
    private Long choiceId;
    private String answer;

    public static ResponseDetailResponse from(ResponseDetail responseDetail) {
        return ResponseDetailResponse.builder()
                .questionId(responseDetail.getQuestion().getQuestionId())
                .choiceId(responseDetail.getChoice() != null ? responseDetail.getChoice().getChoiceId() : null)
                .answer(responseDetail.getAnswer())
                .build();
    }
}
