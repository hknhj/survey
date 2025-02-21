package com.example.survey.domain.response.dto;

import com.example.survey.domain.responseDetail.domain.ResponseDetail;
import com.example.survey.domain.responseDetail.dto.ResponseDetailRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseRequest {
    private List<ResponseDetailRequest> responseDetailRequests;
}
