package com.example.recode.controller;

import com.example.recode.dto.problem.ProblemAddRequestDto;
import com.example.recode.dto.problem.ProblemListDto;
import com.example.recode.dto.problem.ProblemResponseDto;
import com.example.recode.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problems")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("{problemId}")
    public ProblemResponseDto get(@PathVariable Long problemId) {
        return problemService.getProblem(problemId);
    }

    @GetMapping("/list/{challengeId}")
    public List<ProblemListDto> getList(@PathVariable Long challengeId) {
        return problemService.getProblemList(challengeId);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody ProblemAddRequestDto dto) {
        problemService.addProblem(dto);
        return ResponseEntity.ok("문제 추가가 완료되었습니다.");
    }

}
