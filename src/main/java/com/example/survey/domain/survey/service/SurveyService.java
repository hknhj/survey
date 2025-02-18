package com.example.survey.domain.survey.service;

import com.example.survey.domain.survey.domain.Survey;
import com.example.survey.domain.survey.dto.SurveyCreateRequest;
import com.example.survey.domain.survey.dto.SurveyResponse;
import com.example.survey.domain.survey.repository.SurveyRepository;
import com.example.survey.domain.user.domain.User;
import com.example.survey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    // 설문 조사 생성 서비스
    @Transactional
    public SurveyResponse createSurvey(Long userId, SurveyCreateRequest surveyCreateRequest) {

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
        return SurveyResponse.builder()
                .survey(surveyRepository.save(survey))
                .build();
    }

    // 모든 설문 조사 조회 서비스
    public List<SurveyResponse> getAllSurveys() {

        // DB에서 모든 survey 조회
        List<Survey> surveys = surveyRepository.findAll();

        // 스트림 문법을 사용해서 Survey -> SurveyResponse로 변경 후 리턴
        return surveys.stream()
                .map(survey -> SurveyResponse.builder()
                        .survey(survey)
                        .build())
                .toList();
    }

    // 자신이 만든 설문 조사 조회 서비스
    @Transactional
    public List<SurveyResponse> getMySurveys(Long userId) {

        // 유저 아이디를 이용하여 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // userId를 토대로 survey 조회
        List<Survey> mySurveys = surveyRepository.findAllByUser_UserId(userId);

        // Survey List -> SurveyResponse List
        return mySurveys.stream()
                .map(survey -> SurveyResponse.builder()
                        .survey(survey)
                        .build())
                .toList();
    }
}
