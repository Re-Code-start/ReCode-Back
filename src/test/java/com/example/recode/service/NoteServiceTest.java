package com.example.recode.service;

import com.example.recode.domain.Algorithm;
import com.example.recode.dto.note.NoteListDto;
import com.example.recode.dto.note.NoteResponseDto;
import com.example.recode.dto.note.NoteUpdateRequestDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.recode.domain.FeedbackType;
import com.example.recode.domain.Folder;
import com.example.recode.domain.Note;
import com.example.recode.dto.note.NoteAddRequestDto;
import com.example.recode.repository.FolderRepository;
import com.example.recode.repository.NoteRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    public void getNoteListTest() {
        // given
        Folder folder = Folder.builder()
                .name("folderName")
                .notes(new ArrayList<>())
                .algorithms(new ArrayList<>())
                .build();

        List<Note> noteList = Arrays.asList(
                Note.builder()
                        .id(1L)
                        .title("note1")
                        .folder(folder)
                        .createDt(LocalDateTime.now())
                        .build(),
                Note.builder()
                        .id(2L)
                        .title("note2")
                        .folder(folder)
                        .createDt(LocalDateTime.now().minusDays(1))
                        .build()
        );

        when(folderRepository.findById(anyLong())).thenReturn(Optional.of(folder));
        when(noteRepository.findAllByFolder(any(Folder.class))).thenReturn(noteList);

        // when
        List<NoteListDto> result = noteService.getNoteList(1L);

        // then
        verify(folderRepository, times(1)).findById(anyLong());
        verify(noteRepository, times(1)).findAllByFolder(any(Folder.class));

        assertEquals(2, result.size());
        assertEquals("note1", result.get(0).getTitle());
        assertEquals("note2", result.get(1).getTitle());
    }

    @Test()
    public void addNoteTest() {
        //given
        Folder folder = Folder.builder()
                .name("folderName")
                .notes(new ArrayList<>())
                .algorithms(new ArrayList<>())
                .build();

        NoteAddRequestDto dto = NoteAddRequestDto.builder()
                .title("title")
                .link("link")
                .feedbackType(FeedbackType.REVIEW_NOTE)
                .oldCode("oldCode")
                .newCode("newCode")
                .improvement("improvement")
                .comment("comment")
                .folderId(1L)
                .algorithmNameList(Arrays.asList("algorithm1", "algorithm2"))
                .build();

        //when
        when(folderRepository.findById(anyLong())).thenReturn(Optional.of(folder));
        when(noteRepository.save(any(Note.class))).thenAnswer(i -> i.getArgument(0));

        noteService.addNote(dto);

        //then
        verify(folderRepository, times(1)).findById(anyLong());
        verify(noteRepository, times(1)).save(any(Note.class));
        verify(algorithmService, times(1)).addNoteAlgorithm(anyList(), any(Note.class));
    }

    @Test
    public void updateNoteTitleTest() {
        // given
        Note note = Note.builder()
                .id(1L)
                .title("oldTitle")
                .build();

        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        // when
        noteService.updateNoteTitle(1L, "newTitle");

        // then
        verify(noteRepository, times(1)).findById(anyLong());
        assertEquals("newTitle", note.getTitle());
    }

    @Test
    public void updateNoteTest_All() {
        // given
        Note note = Note.builder()
                .id(1L)
                .newCode("oldCode")
                .improvement("oldImprovement")
                .build();

        NoteUpdateRequestDto dto = NoteUpdateRequestDto.builder()
                .newCode("newCode")
                .improvement("newImprovement")
                .algorithmIds(Arrays.asList(1L, 2L))
                .build();

        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        // when
        noteService.updateNote(1L, dto);

        // then
        verify(noteRepository, times(1)).findById(anyLong());
        verify(algorithmService, times(1)).updateNoteAlgorithm(anyList(), any(Note.class));
        assertEquals("newCode", note.getNewCode());
        assertEquals("newImprovement", note.getImprovement());
    }

    @Test
    public void updateNoteTest_Code() {
        // given
        Note note = Note.builder()
                .id(1L)
                .newCode("oldCode")
                .improvement("oldImprovement")
                .build();

        NoteUpdateRequestDto dto = NoteUpdateRequestDto.builder()
                .newCode("newCode")
                .build();

        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        // when
        noteService.updateNote(1L, dto);

        // then
        verify(noteRepository, times(1)).findById(anyLong());
        verify(algorithmService, times(0)).updateNoteAlgorithm(anyList(), any(Note.class));
        assertEquals("newCode", note.getNewCode());
        assertEquals("oldImprovement", note.getImprovement());
    }

    @Test
    public void updateNoteTest_Improvement() {
        // given
        Note note = Note.builder()
                .id(1L)
                .newCode("oldCode")
                .improvement("oldImprovement")
                .build();

        NoteUpdateRequestDto dto = NoteUpdateRequestDto.builder()
                .improvement("newImprovement")
                .build();

        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        // when
        noteService.updateNote(1L, dto);

        // then
        verify(noteRepository, times(1)).findById(anyLong());
        verify(algorithmService, times(0)).updateNoteAlgorithm(anyList(), any(Note.class));
        assertEquals("oldCode", note.getNewCode());
        assertEquals("newImprovement", note.getImprovement());
    }

    @Test
    public void updateNoteTest_Algorithm() {
        // given
        Note note = Note.builder()
                .id(1L)
                .newCode("oldCode")
                .improvement("oldImprovement")
                .build();

        NoteUpdateRequestDto dto = NoteUpdateRequestDto.builder()
                .algorithmIds(Arrays.asList(1L, 2L))
                .build();

        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        // when
        noteService.updateNote(1L, dto);

        // then
        verify(noteRepository, times(1)).findById(anyLong());
        verify(algorithmService, times(1)).updateNoteAlgorithm(anyList(), any(Note.class));
        assertEquals("oldCode", note.getNewCode());
        assertEquals("oldImprovement", note.getImprovement());
    }

    @Test
    public void deleteNoteTest() {
        // given
        Note note = Note.builder()
                .id(1L)
                .title("note")
                .build();

        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        // when
        noteService.deleteNote(1L);

        // then
        verify(noteRepository, times(1)).findById(anyLong());
        verify(noteRepository, times(1)).delete(any(Note.class));
    }

}

