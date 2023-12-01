package com.example.recode.controller;

import com.example.recode.dto.note.NoteAddRequestDto;
import com.example.recode.dto.note.NoteListDto;
import com.example.recode.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/{folderId}")
    public List<NoteListDto> getList(@PathVariable Long folderId) {
        return noteService.getNoteList(folderId);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody NoteAddRequestDto dto) {
        noteService.addNote(dto);
        return ResponseEntity.ok("오답노트가 추가되었습니다.");
    }

}
