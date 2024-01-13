package com.example.recode.controller;

import com.example.recode.dto.algorithm.AlgorithmAddRequestDto;
import com.example.recode.dto.algorithm.AlgorithmListDto;
import com.example.recode.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/algorithms")
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    @GetMapping("/list")
    public List<AlgorithmListDto> getList(@RequestParam Long folderId) {
        return algorithmService.getFolderAlgorithmList(folderId);
    }

    @GetMapping("/list")
    public List<AlgorithmListDto> getList(@RequestParam Long groupId, @RequestParam Long userId) {
        return algorithmService.getGroupRoomAlgorithmList(groupId, userId);
    }

    @PostMapping
    public List<AlgorithmListDto> add(@RequestBody AlgorithmAddRequestDto dto) {
        return algorithmService.addFolderAlgorithm(dto);
    }

    @PutMapping("/{algorithmId}/{algorithmName}")
    public List<AlgorithmListDto> update(@PathVariable Long algorithmId, @PathVariable String algorithmName) {
        return algorithmService.updateAlgorithmName(algorithmId, algorithmName);
    }

    @DeleteMapping("/{algorithmId}")
    public List<AlgorithmListDto> delete(@PathVariable Long algorithmId) {
        return algorithmService.deleteAlgorithm(algorithmId);
    }

}
