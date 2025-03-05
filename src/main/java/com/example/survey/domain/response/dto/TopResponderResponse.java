package com.example.survey.domain.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopResponderResponse {
    private Long userId;
    private Long responseCount;
}
