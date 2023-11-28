package com.example.recode.controller;

import com.example.recode.dto.folder.FolderAddRequestDto;
import com.example.recode.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
public class FolderController {

    private FolderService folderService;

    @PostMapping
    public ResponseEntity add(FolderAddRequestDto dto) {
        folderService.addFolder(dto);
        return ResponseEntity.ok("폴더 추가가 완료되었습니다.");
    }

}
