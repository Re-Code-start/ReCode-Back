package com.example.recode.service;

import com.example.recode.domain.Folder;
import com.example.recode.domain.Note;
import com.example.recode.dto.algorithm.AlgorithmAddRequestDto;
import com.example.recode.dto.algorithm.AlgorithmListDto;
import com.example.recode.repository.AlgorithmRepository;
import com.example.recode.repository.FolderRepository;
import com.example.recode.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;
    private final FolderRepository folderRepository;
    private final NoteRepository noteRepository;

    public List<AlgorithmListDto> getAlgorithmList(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 폴더입니다."));

        return algorithmRepository.findAllByFolder(folder).stream()
                .map(algorithm -> AlgorithmListDto.builder()
                        .id(algorithm.getId())
                        .name(algorithm.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<AlgorithmListDto> addAlgorithm(AlgorithmAddRequestDto dto) {
        Folder folder = folderRepository.findById(dto.getFolderId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 폴더입니다."));

        Note note = noteRepository.findById(dto.getNoteId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 노트입니다."));

        algorithmRepository.save(dto.toEntity(folder, note));

        return getAlgorithmList(dto.getFolderId());
    }

}
