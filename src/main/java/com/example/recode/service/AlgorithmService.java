package com.example.recode.service;

import com.example.recode.domain.Algorithm;
import com.example.recode.domain.Answer;
import com.example.recode.domain.Folder;
import com.example.recode.domain.Group;
import com.example.recode.domain.Note;
import com.example.recode.domain.User_Group;
import com.example.recode.domain.Users;
import com.example.recode.dto.algorithm.FolderAlgorithmAddRequestDto;
import com.example.recode.dto.algorithm.AlgorithmListDto;
import com.example.recode.dto.algorithm.GroupRoomAlgorithmAddRequestDto;
import com.example.recode.repository.AlgorithmRepository;
import com.example.recode.repository.FolderRepository;
import com.example.recode.repository.GroupRepository;
import com.example.recode.repository.UserRepository;
import com.example.recode.repository.User_GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;
    private final FolderRepository folderRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final User_GroupRepository userGroupRepository;

    public Algorithm getAlgorithm(Long algorithmId) {
        return algorithmRepository.findById(algorithmId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 알고리즘입니다."));
    }

    public List<AlgorithmListDto> getFolderAlgorithmList(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 폴더입니다."));

        return algorithmRepository.findAllByFolder(folder).stream()
                .map(algorithm -> AlgorithmListDto.builder()
                        .id(algorithm.getId())
                        .name(algorithm.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<AlgorithmListDto> getGroupRoomAlgorithmList(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 그룹입니다."));

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        User_Group userGroup = userGroupRepository.findByGroupMemberAndGroup(user, group);

        return getUserGroupAlgorithmList(userGroup);
    }

    public List<AlgorithmListDto> addFolderAlgorithm(FolderAlgorithmAddRequestDto dto) {

        Folder folder = folderRepository.findById(dto.getFolderId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 폴더입니다."));

        algorithmRepository.save(dto.toEntity(folder));

        return getFolderAlgorithmList(dto.getFolderId());
    }

    public List<AlgorithmListDto> addGroupRoomAlgorithm(GroupRoomAlgorithmAddRequestDto dto) {
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 그룹입니다."));

        Users user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        User_Group userGroup = userGroupRepository.findByGroupMemberAndGroup(user, group);

        algorithmRepository.save(dto.toEntity(userGroup));

        return getGroupRoomAlgorithmList(dto.getGroupId(), dto.getUserId());
    }

    public void addNoteAlgorithm(List<String> nameList, Note note) {
        for (String name:nameList) {
            algorithmRepository.save(Algorithm.builder().name(name).note(note).build());
        }
    }

    public void addAnswerAlgorithm(List<String> nameList, Answer answer) {
        for (String name:nameList) {
            algorithmRepository.save(Algorithm.builder().name(name).answer(answer).build());
        }
    }

    public List<AlgorithmListDto> updateAlgorithmName(Long algorithmId, String algorithmName) {
        Algorithm algorithm = getAlgorithm(algorithmId);
        getAlgorithm(algorithmId).updateName(algorithmName);

        if (algorithm.getFolder() != null) {
            return getFolderAlgorithmList(algorithm.getFolder().getId());
        }
        else {
            return getUserGroupAlgorithmList(algorithm.getUserGroup());
        }
    }

    public List<AlgorithmListDto> deleteAlgorithm(Long algorithmId) {
        Algorithm algorithm = getAlgorithm(algorithmId);

        algorithmRepository.delete(algorithm);

        if (algorithm.getFolder() != null) {
            return getFolderAlgorithmList(algorithm.getFolder().getId());
        }
        else {
            return getUserGroupAlgorithmList(algorithm.getUserGroup());
        }
    }

    public void updateAlgorithms(List<Long> algorithmIds, Object object) {
        Set<Algorithm> oldAlgorithms;
        if (object instanceof Note) {
            oldAlgorithms = new HashSet<>(((Note) object).getAlgorithms());
        } else if (object instanceof Answer) {
            oldAlgorithms = new HashSet<>(((Answer) object).getAlgorithms());
        } else {
            throw new IllegalArgumentException("Invalid object type");
        }

        Set<Algorithm> newAlgorithms = new HashSet<>();
        for (Long algorithmId : algorithmIds) {
            Algorithm algorithm = getAlgorithm(algorithmId);
            newAlgorithms.add(algorithm);
        }

        List<Algorithm> algorithmsToRemove = new ArrayList<>();
        List<String> algorithmNamesToAdd = new ArrayList<>();

        // 옛 리스트에만 있는 것 -> 삭제
        for (Algorithm algorithm : oldAlgorithms) {
            if (!newAlgorithms.contains(algorithm)) {
                algorithmsToRemove.add(algorithm);
            }
        }
        algorithmRepository.deleteAll(algorithmsToRemove);

        // 새 리스트에만 있는 것 -> 추가
        for (Algorithm algorithm : newAlgorithms) {
            if (!oldAlgorithms.contains(algorithm)) {
                algorithmNamesToAdd.add(algorithm.getName());
            }
        }

        if (object instanceof Note) {
            addNoteAlgorithm(algorithmNamesToAdd, (Note) object);
        } else {
            addAnswerAlgorithm(algorithmNamesToAdd, (Answer) object);
        }
    }

    private List<AlgorithmListDto> getUserGroupAlgorithmList(User_Group userGroup) {
        return algorithmRepository.findAllByUserGroup(userGroup).stream()
                .map(algorithm -> AlgorithmListDto.builder()
                        .id(algorithm.getId())
                        .name(algorithm.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
