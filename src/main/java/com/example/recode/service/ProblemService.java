package com.example.recode.service;

import com.example.recode.domain.Algorithm;
import com.example.recode.domain.Challenge;
import com.example.recode.domain.Problem;
import com.example.recode.domain.Users;
import com.example.recode.dto.problem.ProblemAddRequestDto;
import com.example.recode.dto.problem.ProblemListDto;
import com.example.recode.dto.problem.ProblemResponseDto;
import com.example.recode.dto.problem.ProblemUpdateRequestDto;
import com.example.recode.dto.problem.SolvedMemberListDto;
import com.example.recode.repository.ChallengeRepository;
import com.example.recode.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ChallengeRepository challengeRepository;
    private final UserService userService;

    public ProblemResponseDto getProblem(Long problemId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 문제입니다."));

        List<SolvedMemberListDto> solvedMembers = problem.getAnswers().stream()
                .map(answer -> SolvedMemberListDto.builder()
                        .answerId(answer.getId())
                        .userId(answer.getUser().getId())
                        .userImageUrl(answer.getUser().getImageUrl())
                        .algorithmList(answer.getAlgorithms().stream().map(Algorithm::getName).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return ProblemResponseDto.builder()
                .id(problemId)
                .title(problem.getTitle())
                .link(problem.getLink())
                .category(problem.getCategory())
                .difficulty(problem.getDifficulty())
                .solvedMembers(solvedMembers)
                .build();

    }

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

    public void addProblem(ProblemAddRequestDto dto) {
        Challenge challenge = challengeRepository.findById(dto.getChallengeId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 챌린지입니다."));

        Users user = userService.findCurrentUser();

        // 진행 예정인 챌린지만 문제 추가 가능
        if (!challenge.isUpcoming()) {
            throw new IllegalArgumentException("진행 예정인 챌린지만 새 문제 추가가 가능합니다.");
        }

        // 방장이나 그룹원이 아니라면 에러 발생
        if (!user.equals(challenge.getGroup().getGroupLeader()) || !user.getGroups().contains(challenge.getGroup())) {
            throw new RuntimeException("챌린지는 해당 그룹의 방장이나 그룹원만이 등록할 수 있습니다.");
        }

        // 사용자가 등록한 문제의 수가 10을 넘으면 에러 발생
        if (problemRepository.countByChallengeAndUser(challenge, user) > 10) {
            throw new RuntimeException("한 그룹원 당 최대 10개의 문제만 등록할 수 있습니다.");
        }

        problemRepository.save(dto.toEntity(challenge, user));
    }

    public void updateProblem(Long problemId, ProblemUpdateRequestDto dto) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 문제입니다."));

        // 진행 예정인 챌린지만 문제 추가 가능
        if (!problem.getChallenge().isUpcoming()) {
            throw new IllegalArgumentException("진행 예정인 챌린지만 문제 수정이 가능합니다.");
        }

        problem.updateInfo(dto);
    }

}
