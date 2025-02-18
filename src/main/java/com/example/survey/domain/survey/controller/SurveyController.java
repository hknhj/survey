package com.example.survey.domain.survey.controller;

import com.example.survey.domain.survey.dto.SurveyCreateRequest;
import com.example.survey.domain.survey.dto.SurveyCreateResponse;
import com.example.survey.domain.survey.service.SurveyService;
import com.example.survey.global.DefaultResponseDto;
import com.example.survey.global.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;
    private final JwtTokenProvider jwtTokenProvider;

    // 설문조사 생성
    @PostMapping
    public ResponseEntity<DefaultResponseDto<SurveyCreateResponse>> createSurvey(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody SurveyCreateRequest surveyCreateRequest) {

        // JWT 토큰에서 userId 추출 (Bearer 제거)
        String token = authHeader.substring(7);
        Long userId = jwtTokenProvider.getUserId(token);

        // Service 계층으로 userId, DTO 전달
        SurveyCreateResponse surveyCreateResponse = surveyService.createSurvey(userId, surveyCreateRequest);

        // DefaultResponseDto 생성
        DefaultResponseDto<SurveyCreateResponse> response = DefaultResponseDto.response(
                "설문조사 생성에 성공하였습니다.",
                surveyCreateResponse
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
