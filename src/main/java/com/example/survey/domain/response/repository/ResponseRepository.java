package com.example.survey.domain.response.repository;

import com.example.survey.domain.response.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    @Query("SELECT r.user.userId, COUNT(r) AS responseCount" +
            "FROM Response r " +
            "GROUP BY r.user.user_id " +
            "ORDER BY responseCount DESC")
    List<Object[]> findTopUsersByResponseCount();
}
