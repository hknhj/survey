package com.example.survey.domain.survey.service;

import com.example.survey.domain.question.domain.Question;
import com.example.survey.domain.question.service.QuestionService;
import com.example.survey.domain.response.dto.TopResponderResponse;
import com.example.survey.domain.response.service.ResponseService;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final QuestionService questionService;
    private final ResponseService responseService;

    // 설문 생성 서비스
    @Transactional
    public SurveyResponse createSurvey(Long userId, SurveyCreateRequest surveyCreateRequest) {

        // userId를 사용해 User 엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // Survey 엔티티 변환 (SurveyCreateRequest -> Survey) 및 저장
        Survey survey = surveyRepository.save(surveyCreateRequest.toEntity(user));

        // Question 저장 및 Survey에 추가
        if (surveyCreateRequest.getQuestions() != null) {
            List<Question> questions = surveyCreateRequest.getQuestions().stream()
                    .map(questionCreateRequest -> questionService.createQuestion(survey, questionCreateRequest))
                    .toList();

            survey.addQuestions(questions);
        }

        // Survey 엔티티 -> SurveyResponse 변환 후 반환
        return SurveyResponse.from(survey);
    }

    // 모든 설문 조회 서비스
    public List<SurveyResponse> getAllSurveys() {

        // DB에서 모든 survey 조회
        List<Survey> surveys = surveyRepository.findAll();

        // 스트림 문법을 사용해서 Survey -> SurveyResponse로 변경 후 리턴
        return surveys.stream()
                .map(SurveyResponse::from)
                .toList();
    }

    // 자신이 만든 설문 조회 서비스
    public List<SurveyResponse> getMySurveys(Long userId) {

        // 유저 아이디를 이용하여 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // userId를 토대로 survey 조회
        List<Survey> mySurveys = surveyRepository.findAllByUser_UserId(user.getUserId());

        // Survey List -> SurveyResponse List
        return mySurveys.stream()
                .map(SurveyResponse::from)
                .toList();
    }

    // 설문 개별 조회 서비스
    public SurveyResponse getSurvey(Long surveyId) {

        // surveyId를 통하여 Survey 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("해당 설문조사가 존재하지 않습니다."));

        // Survey -> SurveyResponse
        return SurveyResponse.from(survey);
    }

    // 설문 수정 서비스
    @Transactional
    public SurveyResponse modifySurvey(Long userId, Long surveyId, SurveyRequest surveyRequest) {

        // userId를 통해 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // surveyId를 통해 Survey 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("해당 설문조사가 존재하지 않습니다."));

        /// userId와 survey의 userId가 다르면 SurveyAuthorizationException
        validateSurveyOwner(user.getUserId(), survey);

        // 설문조사 업데이트
        survey.updateSurvey(
                surveyRequest.getTitle(),
                surveyRequest.getDescription(),
                surveyRequest.getStartDate(),
                surveyRequest.getEndDate()
        );

        return SurveyResponse.from(survey);
    }

    // 설문 삭제 서비스
    @Transactional
    public SurveyResponse deleteSurvey(Long userId, Long surveyId) {

        // userId를 통해 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // surveyId를 통해 설문조사 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("해당 설문조사가 존재하지 않습니다."));

        // userId와 survey의 userId가 다르면 SurveyAuthorizationException
        validateSurveyOwner(user.getUserId(), survey);

        // 설문조사 삭제
        surveyRepository.delete(survey);

        return SurveyResponse.from(survey);
    }

    // 설문 할당 서비스
    public SurveyResponse assignSurvey(Long userId) {

        // userId를 통해 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // 설문에 응답한 수가 가장 많은 유저 순서로 정렬
        List<TopResponderResponse> responders = responseService.getTopResponders();

        log.info("responders: {}", responders.toString());

        List<Survey> surveys = new ArrayList<>();

        // 해당 유저가 생성한 설문을 응답이 적은 순으로 정렬
        responders.forEach(responder -> {
                    Long responderId = responder.getUserId();

                    // 해당 유저가 만든 설문을 응답 개수 기준으로 정렬하여 조회
                    List<Survey> surveyListByResponseCount =  surveyRepository.findUserSurveysOrderByResponseCount(responderId);
                    surveys.addAll(surveyListByResponseCount);
        });

        if (surveys.isEmpty()) throw new RuntimeException("할당할 설문이 없습니다.");


        // 가장 위에 있는 설문을 가져오기
        Survey survey = surveyRepository.findById(surveys.get(0).getSurveyId())
                .orElseThrow(() -> new RuntimeException("해당 설문을 찾을 수 없습니다."));

        return SurveyResponse.from(survey);
    }

    private void validateSurveyOwner(Long userId, Survey survey) {
        if (!survey.getUser().getUserId().equals(userId))
            throw new SurveyAuthorizationException();
    }
}
