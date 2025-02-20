package com.example.survey.domain.option.dto;

import com.example.survey.domain.option.domain.Choice;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChoiceResponse {
    private Long optionId;
    private String text;
    private Integer orderNumber;

    // OptionResponse -> Option 변환
    public static ChoiceResponse from(final Choice choice) {
        return ChoiceResponse.builder()
                .optionId(choice.getChoiceId())
                .text(choice.getText())
                .orderNumber(choice.getOrderNumber())
                .build();
    }
}
