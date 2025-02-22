package com.example.survey.domain.responseDetail.service;

import com.example.survey.domain.option.domain.Choice;
import com.example.survey.domain.option.repository.ChoiceRepository;
import com.example.survey.domain.question.domain.Question;
import com.example.survey.domain.question.repository.QuestionRepository;
import com.example.survey.domain.response.domain.Response;
import com.example.survey.domain.responseDetail.domain.ResponseDetail;
import com.example.survey.domain.responseDetail.dto.ResponseDetailRequest;
import com.example.survey.domain.responseDetail.repository.ResponseDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseDetailService {

    private final ResponseDetailRepository responseDetailRepository;
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;

    public ResponseDetail createResponseDetail(Response response, ResponseDetailRequest responseDetailRequest) {

        // 질문 조회
        Question question = questionRepository.findById(responseDetailRequest.getQuestionId())
                .orElseThrow(() -> new RuntimeException("해당 질문이 존재하지 않습니다."));

        // 선택지 조회 및 검증
        Choice choice = validateResponseDetailRequest(responseDetailRequest);

        // ResponseDetail 저장 및 반환
        return responseDetailRepository.save(ResponseDetail.builder()
                .response(response)
                .question(question)
                .choice(choice)
                .answer(responseDetailRequest.getAnswer())
                .build());
    }

    private Choice validateResponseDetailRequest(ResponseDetailRequest responseDetailRequest) {

        Choice choice = null;

        // 선택지 조회
        if (responseDetailRequest.getChoiceId() != null) {
            choice = choiceRepository.findById(responseDetailRequest.getChoiceId())
                    .orElseThrow(() -> new RuntimeException("해당 선택지가 존재하지 않습니다."));
        }

        // 질문에 포함된 선택지인지 확인
        if (choice != null && !choice.getQuestion().getQuestionId().equals(responseDetailRequest.getQuestionId())) {
            throw new RuntimeException("질문에 포함되어있는 선택지가 아닙니다.");
        }

        // choice가 있으면 answer가 없어야 하고, choice가 없으면 answer가 있어야 함
        if ((choice != null && responseDetailRequest.getAnswer() != null) ||
                (choice == null && responseDetailRequest.getAnswer() == null)) {
            throw new RuntimeException("선택형 질문에는 답변을 입력할 수 없으며, 서술형 질문에는 반드시 답변이 있어야 합니다.");
        }

        return choice;
    }
}
