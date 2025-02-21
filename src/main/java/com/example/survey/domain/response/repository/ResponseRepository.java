package com.example.survey.domain.response.repository;

import com.example.survey.domain.response.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
