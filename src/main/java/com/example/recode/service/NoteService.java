package com.example.recode.service;

import com.example.recode.domain.Folder;
import com.example.recode.domain.Note;
import com.example.recode.dto.note.NoteAddRequestDto;
import com.example.recode.dto.note.NoteListDto;
import com.example.recode.dto.note.NoteResponseDto;
import com.example.recode.dto.note.NoteUpdateRequestDto;
import com.example.recode.repository.FolderRepository;
import com.example.recode.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final FolderRepository folderRepository;

    public NoteResponseDto getNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 노트입니다."));

        return NoteResponseDto.builder()
                .title(note.getTitle())
                .link(note.getLink())
                .feedbackType(note.getType())
                .oldCode(note.getOldCode())
                .newCode(note.getNewCode())
                .improvement(note.getImprovement())
                .comment(note.getComment())
                .build();
    }

    @Transactional(readOnly = true)
    public List<NoteListDto> getNoteList(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 폴더입니다."));
        List<Note> notes = noteRepository.findAllByFolder(folder);
        notes.sort(Comparator.comparing(Note::getCreateDt).reversed());
        return notes.stream()
                .map(note -> NoteListDto.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void addNote(NoteAddRequestDto dto) {
        Folder folder = folderRepository.findById(dto.getFolderId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 폴더입니다."));
        Note note = dto.toEntity(folder);
        noteRepository.save(note);
    }

    @Transactional
    public void updateNoteTitle(Long noteId, String noteTitle) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 노트입니다."));

        note.updateTitle(noteTitle);
    }

    @Transactional
    public void updateNote(Long noteId, NoteUpdateRequestDto dto) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 노트입니다."));

        note.updateNewCode(dto.getNewCode());
        note.updateImprovement(dto.getImprovement());
    }

    @Transactional
    public void deleteNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 노트입니다."));

        noteRepository.delete(note);
    }
}
