package com.example.survey.domain.response.domain;

import com.example.survey.domain.responseDetail.domain.ResponseDetail;
import com.example.survey.domain.survey.domain.Survey;
import com.example.survey.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    // 응답이 작성된 시간
    @Column(name = "response_date", nullable = false)
    private LocalDate responseDate;

    @Builder
    public Response(User user, Survey survey, LocalDate responseDate) {
        this.user = user;
        this.survey = survey;
        this.responseDate = responseDate;
    }

    public void addResponseDetail(ResponseDetail responseDetail) {
        this.responseDetails.add(responseDetail);
    }

    public void addResponseDetails(List<ResponseDetail> responseDetails) {
        this.responseDetails.addAll(responseDetails);
    }

}
