package com.example.recode.service;

import com.example.recode.domain.Algorithm;
import com.example.recode.repository.AlgorithmRepository;
import com.example.recode.repository.FolderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
