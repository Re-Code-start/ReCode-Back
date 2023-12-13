package com.example.recode.controller;

import com.example.recode.dto.algorithm.AlgorithmListDto;
import com.example.recode.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/algorithms")
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    @GetMapping("/list/{folderId}")
    public List<AlgorithmListDto> getList(@PathVariable Long folderId) {
        return algorithmService.getAlgorithmList(folderId);
    }

}
