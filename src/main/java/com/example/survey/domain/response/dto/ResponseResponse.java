package com.example.survey.domain.response.dto;

import com.example.survey.domain.response.domain.Response;
import com.example.survey.domain.responseDetail.dto.ResponseDetailResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ResponseResponse {
    private Long responseId;
    private Long userId;
    private Long surveyId;
    private LocalDate responseDate;
    private List<ResponseDetailResponse> responseDetails;

    public static ResponseResponse from(Response response) {
        return ResponseResponse.builder()
                .responseId(response.getResponseId())
                .userId(response.getUser().getUserId())
                .surveyId(response.getSurvey().getSurveyId())
                .responseDate(response.getResponseDate())
                .responseDetails(response.getResponseDetails().stream()
                        .map(ResponseDetailResponse::from)
                        .toList())
                .build();
    }
}
