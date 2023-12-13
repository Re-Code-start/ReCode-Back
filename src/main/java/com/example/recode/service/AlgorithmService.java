package com.example.recode.service;

import com.example.recode.domain.Folder;
import com.example.recode.dto.algorithm.AlgorithmListDto;
import com.example.recode.repository.AlgorithmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;
    private final FolderService folderService;

    public List<AlgorithmListDto> getAlgorithmList(Long folderId) {
        Folder folder = folderService.getFolder(folderId);

        return algorithmRepository.findAllByFolder(folder).stream()
                .map(algorithm -> AlgorithmListDto.builder()
                        .id(algorithm.getId())
                        .name(algorithm.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
