package com.example.recode.service;

import com.example.recode.domain.Challenge;
import com.example.recode.domain.Users;
import com.example.recode.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserService userService;

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

    public void closeFeedbackVote(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 챌린지입니다."));

        Users groupLeader = challenge.getGroup().getGroupLeader();

        Users user = userService.findCurrentUser();

        // 챌린지의 피드백 및 투표 기간을 종료시키려는 사람이 그룹의 방장이 아니면 오류 발생
        if (!user.equals(groupLeader)) {
            throw new RuntimeException("그룹의 방장만이 챌린지의 피드백 및 투표 기간을 종료할 수 있습니다.");
        }

        challenge.closeFeedbackVote();
    }

}
