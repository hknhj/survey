package com.example.survey.domain.survey.repository;

import com.example.survey.domain.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findAllByUser_UserId(Long userId);

    @Query("SELECT s FROM Survey s LEFT JOIN Response r ON s.surveyId = r.survey.surveyId " +
            "WHERE s.user.userId = :userId " +
            "GROUP BY s.surveyId " +
            "ORDER BY COUNT(r.responseId) ASC")
    List<Survey> findUserSurveysOrderByResponseCount(@Param("userId") Long userId);
}
