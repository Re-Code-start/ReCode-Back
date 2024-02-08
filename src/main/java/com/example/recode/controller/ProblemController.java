package com.example.recode.controller;

import com.example.recode.dto.problem.ProblemAddRequestDto;
import com.example.recode.dto.problem.ProblemListDto;
import com.example.recode.dto.problem.ProblemResponseDto;
import com.example.recode.dto.problem.ProblemUpdateRequestDto;
import com.example.recode.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @PatchMapping("/{problemId}")
    public ResponseEntity update(@PathVariable Long problemId, @RequestBody ProblemUpdateRequestDto dto) {
        problemService.updateProblem(problemId, dto);
        return ResponseEntity.ok("문제 수정이 완료되었습니다.");
    }

    @DeleteMapping("/{problemId}")
    public ResponseEntity delete(@PathVariable Long problemId) {
        problemService.deleteProblem(problemId);
        return ResponseEntity.ok("문제 삭제가 완료되었습니다.");
    }

}
