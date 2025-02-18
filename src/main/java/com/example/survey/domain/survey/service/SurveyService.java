package com.example.survey.domain.survey.service;

import com.example.survey.domain.survey.domain.Survey;
import com.example.survey.domain.survey.dto.SurveyCreateRequest;
import com.example.survey.domain.survey.dto.SurveyCreateResponse;
import com.example.survey.domain.survey.repository.SurveyRepository;
import com.example.survey.domain.user.domain.User;
import com.example.survey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    @Transactional
    public SurveyCreateResponse createSurvey(Long userId, SurveyCreateRequest surveyCreateRequest) {

        // userId를 사용해 User 엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // DTO를 토대로 survey 엔티티 생성
        Survey survey = Survey.builder()
                .user(user)
                .title(surveyCreateRequest.getTitle())
                .description(surveyCreateRequest.getDescription())
                .startDate(surveyCreateRequest.getStartDate())
                .endDate(surveyCreateRequest.getEndDate())
                .build();

        // DB에 저장
        return SurveyCreateResponse.builder()
                .survey(surveyRepository.save(survey))
                .build();
    }

}
