package com.example.recode.service;

import com.example.recode.domain.Answer;
import com.example.recode.domain.Problem;
import com.example.recode.domain.Users;
import com.example.recode.dto.answer.AnswerAddRequestDto;
import com.example.recode.dto.answer.AnswerUpdateRequestDto;
import com.example.recode.repository.AnswerRepository;
import com.example.recode.repository.ProblemRepository;
import com.example.recode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final AlgorithmService algorithmService;

    public void addAnswer(AnswerAddRequestDto dto) {
        Users user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
        Problem problem = problemRepository.findById(dto.getProblemId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 문제입니다."));

        if (answerRepository.existsByProblemAndUser(problem, user)) {
            new IllegalAccessException("이미 등록된 풀이 코드가 존재합니다.");
        }
        else {
            Answer answer = answerRepository.save(dto.toEntity(user, problem));

            algorithmService.addAnswerAlgorithm(dto.getAlgorithmNameList(), answer);
        }
    }

    public void updateAnswer(Long answerId, AnswerUpdateRequestDto dto) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 풀이 코드입니다."));

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
