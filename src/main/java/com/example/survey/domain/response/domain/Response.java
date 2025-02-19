package com.example.survey.domain.response.domain;

import com.example.survey.domain.responseDetail.domain.ResponseDetail;
import com.example.survey.domain.survey.domain.Survey;
import com.example.survey.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "response")
@NoArgsConstructor
@Getter
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long responseId;

    // 답변한 유저의 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 답변이 속한 질문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    // 질문별 응답 목록
    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResponseDetail> responseDetails = new ArrayList<>();

    @Builder
    public Response(User user, Survey survey) {
        this.user = user;
        this.survey = survey;
    }

    public void addResponseDetail(ResponseDetail responseDetail) {
        this.responseDetails.add(responseDetail);
    }

}
