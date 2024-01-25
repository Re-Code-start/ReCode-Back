package com.example.recode.service;

import com.example.recode.domain.Answer;
import com.example.recode.domain.Challenge;
import com.example.recode.domain.Users;
import com.example.recode.domain.Vote;
import com.example.recode.repository.AnswerRepository;
import com.example.recode.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteService {

    private final VoteRepository voteRepository;
    private final AnswerRepository answerRepository;
    private final UserService userService;

    public void add(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 풀이 코드입니다."));

        Challenge challenge = answer.getProblem().getChallenge();

        Users voter = userService.findCurrentUser();

        // 챌린지가 마감되지 않았다면 에러 발생
        if (challenge.getEndDt().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("마감된 챌린지에 대해서만 투표 가능합니다.");
        }

        // 이미 해당 문제에 베스트 코더 투표를 한 적이 있다면 에러 발생
        if (voteRepository.existsByVoterAndProblem(voter, answer.getProblem())) {
            throw new RuntimeException("한 문제에 한 번씩만 투표가 가능합니다.");
        }

        // 방장이 투표를 종료시켰다면 에러 발생
        if (!challenge.isFeedbackYn()) {
            throw new RuntimeException("투표 가능 기간이 아닙니다.");
        }

        // 챌린지 마감 후 3일이 지났다면 에러 발생
        if (challenge.getEndDt().plusDays(3).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("투표 가능 기간이 아닙니다.");
        }

        Vote vote = Vote.builder()
                .voter(voter)
                .answer(answer)
                .problem(answer.getProblem())
                .build();

        voteRepository.save(vote);
    }

}
