package com.example.survey.domain.response.controller;

import com.example.survey.domain.jwt.JwtTokenProvider;
import com.example.survey.domain.response.dto.ResponseRequest;
import com.example.survey.domain.response.dto.ResponseResponse;
import com.example.survey.domain.response.service.ResponseService;
import com.example.survey.global.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ResponseController {

    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/surveys/{surveyId}/responses")
    public ResponseEntity<DefaultResponse<ResponseResponse>> submitSurveyResponse(
            @PathVariable Long surveyId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ResponseRequest responseRequest) {

        // jwt token에서 userId 파싱
        String jwtToken = authHeader.substring(7);
        Long userId = jwtTokenProvider.getUserId(jwtToken);

        // 설문 응답 저장
        ResponseResponse responseResponse = responseService.submitSurveyResponse(surveyId, userId, responseRequest);

        DefaultResponse<ResponseResponse> response = DefaultResponse.response(
                "설문 응답 생성이 완료되었습니다.",
                responseResponse
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
