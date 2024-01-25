package com.example.recode.service;

import com.example.recode.domain.Answer;
import com.example.recode.domain.Challenge;
import com.example.recode.domain.Users;
import com.example.recode.dto.feedback.FeedbackAddDto;
import com.example.recode.repository.AnswerRepository;
import com.example.recode.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final AnswerRepository answerRepository;
    private final UserService userService;
    private final ChallengeService challengeService;

    public void addAnswerFeedback(FeedbackAddDto dto) {
        Answer answer = answerRepository.findById(dto.getReferenceId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 풀이 코드입니다."));

        Users user = userService.findCurrentUser();

        Challenge challenge = answer.getProblem().getChallenge();

        challengeService.validateChallenge(challenge);

        feedbackRepository.save(dto.toEntityWithAnswer(user, answer));
    }

}
