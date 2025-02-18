package com.example.survey.domain.survey.controller;

import com.example.survey.domain.survey.dto.SurveyCreateRequest;
import com.example.survey.domain.survey.dto.SurveyResponse;
import com.example.survey.domain.survey.service.SurveyService;
import com.example.survey.global.DefaultResponseDto;
import com.example.survey.global.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;
    private final JwtTokenProvider jwtTokenProvider;

    // 설문조사 생성
    @PostMapping
    public ResponseEntity<DefaultResponseDto<SurveyResponse>> createSurvey(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody SurveyCreateRequest surveyCreateRequest) {

        // JWT 토큰에서 userId 추출 (Bearer 제거)
        String token = authHeader.substring(7);
        Long userId = jwtTokenProvider.getUserId(token);

        // Service 계층으로 userId, DTO 전달
        SurveyResponse surveyResponse = surveyService.createSurvey(userId, surveyCreateRequest);

        // DefaultResponseDto 생성
        DefaultResponseDto<SurveyResponse> response = DefaultResponseDto.response(
                "설문조사 생성에 성공하였습니다.",
                surveyResponse
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 모든 설문조사 조회
    @GetMapping
    public ResponseEntity<DefaultResponseDto<List<SurveyResponse>>> getAllSurveys() {

        // service 계층을 사용하여 모든 설문조사 리스트 추출
        List<SurveyResponse> surveyResponseList = surveyService.getAllSurveys();

        DefaultResponseDto<List<SurveyResponse>> response = DefaultResponseDto.response(
                "모든 설문 조사 리스트 검색 성공"
                        , surveyResponseList
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
//
//    // 자신이 만든 설문조사 조회
//    @GetMapping("/my")
//    public ResponseEntity<DefaultResponseDto<List<SurveyResponse>>> getMySurveys() {
//    }
//
//    // 개별 설문조사 조회
//    @GetMapping("/{surveyId}")
//    public ResponseEntity<DefaultResponseDto<SurveyResponse>> getSurvey(@PathVariable Long surveyId) {
//
//    }
//
//    // 설문조사 수정
//    @PatchMapping("/{surveyId}")
//    public ResponseEntity<DefaultResponseDto<SurveyResponse>> updateSurvey(@PathVariable Long surveyId) {
//
//    }
//
//    // 설문조사 삭제
//    @DeleteMapping("/{surveyId}")
//    public ResponseEntity<DefaultResponseDto<SurveyResponse>> deleteSurvey(@PathVariable Long surveyId) {
//
//    }
}
