package com.example.recode.service;

import com.example.recode.domain.Folder;
import com.example.recode.domain.Note;
import com.example.recode.dto.note.NoteAddRequestDto;
import com.example.recode.dto.note.NoteListDto;
import com.example.recode.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final FolderService folderService;

    @Transactional(readOnly = true)
    public List<NoteListDto> getNoteList(Long folderId) {
        Folder folder = folderService.getFolder(folderId);
        List<Note> notes = noteRepository.findAllByFolder(folder);
        return notes.stream()
                .map(note -> NoteListDto.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void addNote(NoteAddRequestDto dto) {
        Folder folder = folderService.getFolder(dto.getFolderId());
        Note note = dto.toEntity(folder);
        noteRepository.save(note);
    }
}
