package com.example.survey.domain.survey.repository;

import com.example.survey.domain.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
