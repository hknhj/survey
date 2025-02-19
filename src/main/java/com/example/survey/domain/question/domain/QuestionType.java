package com.example.survey.domain.question.domain;

import java.util.Arrays;

public enum QuestionType {
    TEXT,
    SINGLE_CHOICE,
    MULTI_CHOICE,
    ;

    public static QuestionType fromString(String text) {
        return Arrays.stream(QuestionType.values())
                .filter(type -> type.name().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 QuestionType입니다."));

    }

    public static String toString(QuestionType type) {
        return type.name();
    }
}
