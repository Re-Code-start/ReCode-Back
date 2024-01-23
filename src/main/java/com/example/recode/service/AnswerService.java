package com.example.recode.service;

import com.example.recode.domain.Algorithm;
import com.example.recode.domain.Answer;
import com.example.recode.domain.Group;
import com.example.recode.domain.Problem;
import com.example.recode.domain.Users;
import com.example.recode.dto.answer.AnswerAddRequestDto;
import com.example.recode.dto.answer.AnswerListDto;
import com.example.recode.dto.answer.AnswerResponseDto;
import com.example.recode.dto.answer.AnswerUpdateRequestDto;
import com.example.recode.dto.note.NoteListDto;
import com.example.recode.repository.AnswerRepository;
import com.example.recode.repository.GroupRepository;
import com.example.recode.repository.ProblemRepository;
import com.example.recode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final GroupRepository groupRepository;
    private final AlgorithmService algorithmService;

    public AnswerResponseDto getAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 풀이입니다."));

        return AnswerResponseDto.builder()
                .title(answer.getProblem().getTitle())
                .link(answer.getProblem().getLink())
                .code(answer.getCode())
                .comment(answer.getComment())
                .algorithmList(answer.getAlgorithms().stream().map(Algorithm::getName).collect(Collectors.toList()))
                .build();
    }

    public List<AnswerListDto> getAnswerList(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 그룹방입니다."));

        return group.getChallenges().stream()
                .flatMap(challenge -> challenge.getProblems().stream())
                .flatMap(problem -> problem.getAnswers().stream())
                .map(answer -> AnswerListDto.builder()
                        .id(answer.getId())
                        .title(answer.getProblem().getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    public void addAnswer(AnswerAddRequestDto dto) {
        Users user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
        Problem problem = problemRepository.findById(dto.getProblemId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 문제입니다."));

        if (answerRepository.existsByProblemAndUser(problem, user)) {
            throw new RuntimeException("이미 등록된 풀이 코드가 존재합니다.");
        }
        else {
            Answer answer = answerRepository.save(dto.toEntity(user, problem));

            algorithmService.addAnswerAlgorithm(dto.getAlgorithmNameList(), answer);
        }
    }

    public void updateAnswer(Long answerId, AnswerUpdateRequestDto dto) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 풀이 코드입니다."));

        LocalDateTime challengeEndDt = answer.getProblem().getChallenge().getEndDt();
        if (LocalDateTime.now().isAfter(challengeEndDt)) {
            throw new RuntimeException("종료된 챌린지에 대해서는 풀이 코드를 수정할 수 없습니다.");
        }

        if (dto.getCode() != null) {
            answer.updateCode(dto.getCode());
        }
        if (dto.getComment() != null) {
            answer.updateComment(dto.getComment());
        }
        if (dto.getAlgorithmIds() != null) {
            algorithmService.updateAlgorithms(dto.getAlgorithmIds(), answer);
        }
    }

}
