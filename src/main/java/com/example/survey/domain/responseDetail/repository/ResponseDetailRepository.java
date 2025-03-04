package com.example.survey.domain.responseDetail.repository;

import com.example.survey.domain.responseDetail.domain.ResponseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseDetailRepository extends JpaRepository<ResponseDetail, Long> {
}
