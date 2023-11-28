package com.example.recode.service;

import com.example.recode.domain.Folder;
import com.example.recode.domain.User;
import com.example.recode.dto.folder.FolderAddRequestDto;
import com.example.recode.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final UserService userService;
    private final FolderRepository folderRepository;

    @Transactional
    public void addFolder(FolderAddRequestDto dto) {
        User user = userService.getUser(dto.getUserId());
        Folder folder = dto.toEntity();
        folderRepository.save(folder);
    }
}
