package com.example.recode.service;

import com.example.recode.domain.Folder;
import com.example.recode.domain.FolderType;
import com.example.recode.domain.User_Group;
import com.example.recode.domain.Users;
import com.example.recode.dto.folder.FolderAddRequestDto;
import com.example.recode.dto.folder.FolderListDto;
import com.example.recode.repository.FolderRepository;
import com.example.recode.repository.GroupRepository;
import com.example.recode.repository.UserRepository;
import com.example.recode.repository.User_GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final User_GroupRepository user_groupRepository;

    @Transactional(readOnly = true)
    public Folder getFolder(Long folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 폴더입니다."));
    }

    @Transactional(readOnly = true)
    public List<FolderListDto> getFolderList(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 정보입니다."));

        List<FolderListDto> folders = folderRepository.findAllByUser(user).stream()
                .map(folder -> FolderListDto.builder()
                        .id(folder.getId())
                        .name(folder.getName())
                        .folderType(FolderType.FOLDER)
                        .build())
                .collect(Collectors.toList());

        List<FolderListDto> groups = user_groupRepository.findAllByGroupMember(user).stream()
                .map(userGroup -> FolderListDto.builder()
                        .id(userGroup.getGroup().getId())
                        .name(userGroup.getGroup().getName())
                        .folderType(FolderType.GROUP)
                        .build())
                .collect(Collectors.toList());

        folders.addAll(groups);

        return folders;
    }

    @Transactional
    public void addFolder(FolderAddRequestDto dto) {
        Users users = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 정보입니다."));
        Folder folder = dto.toEntity(users);
        folderRepository.save(folder);
    }

    @Transactional
    public void updateFolderName(Long folderId, String folderName) {
        Folder folder = getFolder(folderId);
        folder.updateFolderName(folderName);
    }

    @Transactional
    public void deleteFolder(Long folderId) {
        folderRepository.delete(getFolder(folderId));
    }
}
