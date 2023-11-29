package com.example.recode.service;

import com.example.recode.domain.Folder;
import com.example.recode.domain.Note;
import com.example.recode.dto.note.NoteAddRequestDto;
import com.example.recode.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final FolderService folderService;

    @Transactional
    public void addNote(NoteAddRequestDto dto) {
        Folder folder = folderService.getFolder(dto.getFolderId());
        Note note = dto.toEntity(folder);
        noteRepository.save(note);
    }
}
