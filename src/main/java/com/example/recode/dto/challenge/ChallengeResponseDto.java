package com.example.recode.dto.challenge;

import com.example.recode.domain.Challenge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeResponseDto {

    private Long id;

    private String name;            // 챌린지명

    private LocalDateTime startDt;  // 챌린지시작일시

    private LocalDateTime endDt;    // 챌린지마감일시

    private LocalDateTime createDt; // 챌린지생성일시

    private double participation;   // 참여도

    private boolean feedbackVoteYN;     // 피드백 & 투표 가능여부

    public ChallengeResponseDto(Challenge challenge) {
        this.id = challenge.getId();
        this.name = challenge.getName();
        this.startDt = challenge.getStartDt();
        this.endDt = challenge.getEndDt();
        this.participation = challenge.getParticipation();
        this.feedbackVoteYN = challenge.isFeedbackVoteYN();
    }

}
