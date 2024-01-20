package com.example.recode.service;

import com.example.recode.domain.Algorithm;
import com.example.recode.dto.note.NoteResponseDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.recode.domain.FeedbackType;
import com.example.recode.domain.Note;
import com.example.recode.repository.FolderRepository;
import com.example.recode.repository.NoteRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @InjectMocks
    NoteService noteService;

    @Mock
    FolderRepository folderRepository;

    @Mock
    NoteRepository noteRepository;

    @Mock
    AlgorithmService algorithmService;

    @Test
    public void getNoteTest() {
        //given
        Note note = Note.builder()
                .title("title")
                .link("link")
                .type(FeedbackType.REVIEW_NOTE)
                .oldCode("oldCode")
                .newCode("newCode")
                .improvement("improvement")
                .comment("comment")
                .algorithms(Arrays.asList(Algorithm.builder().name("algorithm1").build(), Algorithm.builder().name("algorithm2").build()))
                .build();

        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        // when
        NoteResponseDto result = noteService.getNote(1L);

        // then
        verify(noteRepository, times(1)).findById(anyLong());

        assertEquals("title", result.getTitle());
        assertEquals("link", result.getLink());
        assertEquals(FeedbackType.REVIEW_NOTE, result.getFeedbackType());
        assertEquals("oldCode", result.getOldCode());
        assertEquals("newCode", result.getNewCode());
        assertEquals("improvement", result.getImprovement());
        assertEquals("comment", result.getComment());
        assertEquals(Arrays.asList("algorithm1", "algorithm2"), result.getAlgorithmList());
    }

}

