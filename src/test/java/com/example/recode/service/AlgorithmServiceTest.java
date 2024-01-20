package com.example.recode.service;

import com.example.recode.domain.Algorithm;
import com.example.recode.domain.Folder;
import com.example.recode.domain.Note;
import com.example.recode.dto.algorithm.AlgorithmAddRequestDto;
import com.example.recode.dto.algorithm.AlgorithmListDto;
import com.example.recode.repository.AlgorithmRepository;
import com.example.recode.repository.FolderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlgorithmServiceTest {

    @InjectMocks
    AlgorithmService algorithmService;

    @Mock
    AlgorithmRepository algorithmRepository;

    @Mock
    FolderRepository folderRepository;

    @Test
    public void getAlgorithmTest() {
        // given
        Algorithm algorithm = Algorithm.builder()
                .name("name")
                .build();

        when(algorithmRepository.findById(anyLong())).thenReturn(Optional.of(algorithm));

        // when
        Algorithm result = algorithmService.getAlgorithm(1L);

        // then
        verify(algorithmRepository, times(1)).findById(anyLong());
        assertEquals("name", result.getName());
    }

    @Test
    public void getAlgorithmListTest() {
        // given
        Folder folder = Folder.builder().id(1L).build();

        List<Algorithm> algorithmList = Arrays.asList(
                Algorithm.builder().id(1L).name("algorithm1").build(),
                Algorithm.builder().id(2L).name("algorithm2").build()
        );

        when(folderRepository.findById(anyLong())).thenReturn(Optional.of(folder));
        when(algorithmRepository.findAllByFolder(any(Folder.class))).thenReturn(algorithmList);

        // when
        List<AlgorithmListDto> result = algorithmService.getAlgorithmList(1L);

        // then
        verify(folderRepository, times(1)).findById(anyLong());
        verify(algorithmRepository, times(1)).findAllByFolder(any(Folder.class));

        assertEquals(2, result.size());
        assertEquals("algorithm1", result.get(0).getName());
        assertEquals("algorithm2", result.get(1).getName());
    }

    @Test
    public void addFolderAlgorithmTest() {
        // given
        Folder folder = Folder.builder().id(1L).build();

        AlgorithmAddRequestDto dto = AlgorithmAddRequestDto.builder()
                .folderId(1L)
                .algorithmName("newAlgorithm")
                .build();

        when(folderRepository.findById(anyLong())).thenReturn(Optional.of(folder));
        when(algorithmRepository.save(any(Algorithm.class))).thenAnswer(i -> i.getArgument(0));

        // when
        algorithmService.addFolderAlgorithm(dto);

        // then
        verify(algorithmRepository, times(1)).save(any(Algorithm.class));
    }

    @Test
    public void addNoteAlgorithmTest() {
        // given
        Note note = Note.builder().id(1L).build();
        List<String> nameList = Arrays.asList("algorithm1", "algorithm2");

        when(algorithmRepository.save(any(Algorithm.class))).thenAnswer(i -> i.getArgument(0));

        // when
        algorithmService.addNoteAlgorithm(nameList, note);

        // then
        verify(algorithmRepository, times(nameList.size())).save(any(Algorithm.class));
    }

}
