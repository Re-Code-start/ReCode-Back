package com.example.recode.service;

import com.example.recode.domain.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    public void validateChallenge(Challenge challenge) {
        // 챌린지가 마감되지 않았다면 에러 발생
        if (challenge.getEndDt().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("마감된 챌린지에 대해서만 피드백 등록이 가능합니다.");
        }

        // 방장이 피드백을 종료시켰다면 에러 발생
        if (!challenge.isFeedbackVoteYN()) {
            throw new RuntimeException("피드백 가능 기간이 아닙니다.");
        }

        // 챌린지 마감 후 3일이 지났다면 에러 발생
        if (challenge.getEndDt().plusDays(3).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("피드백 가능 기간이 아닙니다.");
        }
    }

}
