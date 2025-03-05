package com.example.survey.domain.response.service;

import com.example.survey.domain.response.domain.Response;
import com.example.survey.domain.response.dto.ResponseRequest;
import com.example.survey.domain.response.dto.ResponseResponse;
import com.example.survey.domain.response.dto.TopResponderResponse;
import com.example.survey.domain.response.repository.ResponseRepository;
import com.example.survey.domain.responseDetail.domain.ResponseDetail;
import com.example.survey.domain.responseDetail.service.ResponseDetailService;
import com.example.survey.domain.survey.domain.Survey;
import com.example.survey.domain.survey.repository.SurveyRepository;
import com.example.survey.domain.user.domain.User;
import com.example.survey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;
    private final ResponseDetailService responseDetailService;

    // 설문 응답 등록
    @Transactional
    public ResponseResponse submitSurveyResponse(Long surveyId, Long userId, ResponseRequest responseRequest) {

        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        // 설문 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("해당 설문이 존재하지 않습니다."));

        // 의존성을 해결하기 위해 Response 먼저 저장
        Response response = responseRepository.save(Response.builder()
                .user(user)
                .survey(survey)
                .responseDate(LocalDate.now())
                .build());

        // 응답 파싱
        if (responseRequest.getResponseDetailRequests() != null) {
            List<ResponseDetail> responseDetails = responseRequest.getResponseDetailRequests().stream()
                    .map(responseDetailCreateRequest -> responseDetailService.createResponseDetail(response, responseDetailCreateRequest))
                    .toList();

            response.addResponseDetails(responseDetails);
        }

        return ResponseResponse.from(response);
    }

    // 설문 응답 횟수가 많은 유저를 내림차순으로 정렬하여 조회
    public List<TopResponderResponse> getTopResponders() {
        return responseRepository.findTopUsersByResponseCount().stream()
                .map(result -> new TopResponderResponse((Long) result[0], (Long) result[1]))
                .toList();
    }
}
