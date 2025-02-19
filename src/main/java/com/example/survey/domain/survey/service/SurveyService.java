package com.example.survey.domain.survey.service;

import com.example.survey.domain.question.domain.Question;
import com.example.survey.domain.question.service.QuestionService;
import com.example.survey.domain.survey.domain.Survey;
import com.example.survey.domain.survey.dto.SurveyCreateRequest;
import com.example.survey.domain.survey.dto.SurveyRequest;
import com.example.survey.domain.survey.dto.SurveyResponse;
import com.example.survey.domain.survey.exception.SurveyAuthorizationException;
import com.example.survey.domain.survey.repository.SurveyRepository;
import com.example.survey.domain.user.domain.User;
import com.example.survey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final QuestionService questionService;

    // 설문 조사 생성 서비스
    @Transactional
    public SurveyResponse createSurvey(Long userId, SurveyCreateRequest surveyCreateRequest) {

        // userId를 사용해 User 엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // Survey 생성
        Survey survey = Survey.builder()
                .user(user)
                .title(surveyCreateRequest.getTitle())
                .description(surveyCreateRequest.getDescription())
                .startDate(surveyCreateRequest.getStartDate())
                .endDate(surveyCreateRequest.getEndDate())
                .build();

        // DTO에 있는 질문들을 Question DB에 저장하고, Survey에 추가
        if (surveyCreateRequest.getQuestions() != null) {
            surveyCreateRequest.getQuestions().forEach(questionRequest -> {
                Question question = questionService.createQuestion(survey, questionRequest);
                survey.addQuestion(question);
            });
        }

        Survey savedSurvey = surveyRepository.save(survey);

        // DB에 저장
        return SurveyResponse.builder()
                .survey(savedSurvey)
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

    // 설문 조사 개별 조회 서비스
    public SurveyResponse getSurvey(Long surveyId) {

        // surveyId를 통하여 Survey 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("해당 설문조사가 존재하지 않습니다."));

        // Survey -> SurveyResponse
        return SurveyResponse.builder()
                .survey(survey)
                .build();
    }

    // 설문 조사 수정 서비스
    @Transactional
    public SurveyResponse modifySurvey(Long userId, Long surveyId, SurveyRequest surveyRequest) {

        // userId를 통해 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // surveyId를 통해 Survey 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("해당 설문조사가 존재하지 않습니다."));

        /// userId와 survey의 userId가 다르면 SurveyAuthorizationException
        validateSurveyOwner(userId, survey);

        // 설문조사 업데이트
        survey.updateSurvey(
                surveyRequest.getTitle(),
                surveyRequest.getDescription(),
                surveyRequest.getStartDate(),
                surveyRequest.getEndDate()
        );

        return SurveyResponse.builder()
                .survey(survey)
                .build();
    }

    // 설문 조사 삭제 서비스
    @Transactional
    public SurveyResponse deleteSurvey(Long userId, Long surveyId) {

        // userId를 통해 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // surveyId를 통해 설문조사 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("해당 설문조사가 존재하지 않습니다."));

        // userId와 survey의 userId가 다르면 SurveyAuthorizationException
        validateSurveyOwner(userId, survey);

        // 설문조사 삭제
        surveyRepository.delete(survey);

        return SurveyResponse.builder()
                .survey(survey)
                .build();
    }

    private void validateSurveyOwner(Long userId, Survey survey) {
        if (!survey.getUser().getUserId().equals(userId))
            throw new SurveyAuthorizationException();
    }
}
