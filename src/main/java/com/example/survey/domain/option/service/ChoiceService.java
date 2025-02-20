package com.example.survey.domain.option.service;

import com.example.survey.domain.option.domain.Choice;
import com.example.survey.domain.option.dto.ChoiceCreateRequest;
import com.example.survey.domain.option.repository.ChoiceRepository;
import com.example.survey.domain.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChoiceService {

    private final ChoiceRepository choiceRepository;

    // Option 생성 메서드
    public Choice createChoice(Question question, ChoiceCreateRequest choiceCreateRequest) {

        // Option entity 생성 및 저장
        return choiceRepository.save(choiceCreateRequest.toEntity(question));
    }

}
