package com.example.survey.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DefaultResponseDto<T> {

    private String responseMessage;
    private T data;

    public DefaultResponseDto(final String responseMessage) {
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static <T> DefaultResponseDto<T> response(final String responseMessage){
        return response(responseMessage, null);
    }

    public static <T> DefaultResponseDto<T> response(final String responseMessage, final T data){
        return DefaultResponseDto.<T>builder()
                .responseMessage(responseMessage)
                .data(data)
                .build();
    }
}
