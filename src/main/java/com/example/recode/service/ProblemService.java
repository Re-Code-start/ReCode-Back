package com.example.recode.service;

import com.example.recode.domain.Challenge;
import com.example.recode.domain.Users;
import com.example.recode.dto.problem.ProblemListDto;
import com.example.recode.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ChallengeRepository challengeRepository;
    private final UserService userService;

    public List<ProblemListDto> getProblemList(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 챌린지입니다."));

        Users user = userService.findCurrentUser();

        return challenge.getProblems().stream()
                .map(problem -> ProblemListDto.builder()
                        .id(problem.getId())
                        .title(problem.getTitle())
                        .category(problem.getCategory())
                        .difficulty(problem.getDifficulty())
                        .bestCoderId(!challenge.isFeedbackVoteYN() ? problem.getBestCoder().getId() : null)
                        .bestCoderImageUrl(!challenge.isFeedbackVoteYN() ? problem.getBestCoder().getImageUrl() : null)
                        .userAnswerYN(problem.getAnswers().stream().anyMatch(answer -> answer.getUser().equals(user)))
                        .solvedMemberCount(problem.getAnswers().size())
                        .totalMemberCount(challenge.getGroup().getCurrentUsers())
                        .build())
                .collect(Collectors.toList());
    }

}
