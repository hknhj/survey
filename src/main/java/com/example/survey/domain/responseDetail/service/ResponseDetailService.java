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

        // 선택지 조회
        Choice choice = null;
        if (responseDetailRequest.getChoiceId() != null) {
            choice = choiceRepository.findById(responseDetailRequest.getChoiceId())
                    .orElseThrow(() -> new RuntimeException("해당 선택지가 존재하지 않습니다."));
        }

        // 선택지가 질문에 포함되어 있는지 확인
        if (choice != null)
            if (!question.getQuestionId().equals(choice.getQuestion().getQuestionId()))
                throw new RuntimeException("질문에 포함되어있는 선택지가 아닙니다.");

        // 선택지 validation 코드 수정 필요
        // choiceId가 있으면 answer가 null, vice versa 확인 코드 필요

        // ResponseDetail 저장 및 반환
        return responseDetailRepository.save(ResponseDetail.builder()
                .response(response)
                .question(question)
                .choice(choice)
                .answer(responseDetailRequest.getAnswer())
                .build());
    }


}
