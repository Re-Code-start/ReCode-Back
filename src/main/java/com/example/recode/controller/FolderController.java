package com.example.recode.controller;

import com.example.recode.dto.folder.FolderAddRequestDto;
import com.example.recode.dto.folder.FolderListDto;
import com.example.recode.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
public class FolderController {

    private final FolderService folderService;

    @GetMapping("/list/{userId}")
    public List<FolderListDto> getList(@PathVariable Long userId) {
        return folderService.getFolderList(userId);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody FolderAddRequestDto dto) {
        folderService.addFolder(dto);
        return ResponseEntity.ok("폴더 추가가 완료되었습니다.");
    }

    @PutMapping("/{folderId}/{folderName}")
    public ResponseEntity<String> update(@PathVariable Long folderId, @PathVariable String folderName) {
        folderService.updateFolderName(folderId, folderName);
        return ResponseEntity.ok("폴더명 수정이 완료되었습니다.");
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity delete(@PathVariable Long folderId){
        folderService.deleteFolder(folderId);
        return ResponseEntity.ok("폴더 삭제가 완료되었습니다.");
    }

}
