package com.example.recode.service;

import com.example.recode.domain.Folder;
import com.example.recode.domain.Users;
import com.example.recode.dto.folder.FolderAddRequestDto;
import com.example.recode.dto.folder.FolderListDto;
import com.example.recode.repository.FolderRepository;
import com.example.recode.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FolderServiceTest {

    @InjectMocks
    FolderService folderService;

    @Mock
    FolderRepository folderRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void getFolderTest() {
        // given
        Folder folder = Folder.builder()
                .name("name")
                .build();

        when(folderRepository.findById(anyLong())).thenReturn(Optional.of(folder));

        // when
        Folder result = folderService.getFolder(1L);

        // then
        verify(folderRepository, times(1)).findById(anyLong());
        assertEquals("name", result.getName());
    }

    @Test
    public void getFolderListTest() {
        // given
        Users user = Users.builder().id(1L).build();

        List<Folder> folderList = Arrays.asList(
                Folder.builder().id(1L).name("folder1").build(),
                Folder.builder().id(2L).name("folder2").build()
        );

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(folderRepository.findAllByUser(any(Users.class))).thenReturn(folderList);

        // when
        List<FolderListDto> result = folderService.getFolderList(1L);

        // then
        verify(userRepository, times(1)).findById(anyLong());
        verify(folderRepository, times(1)).findAllByUser(any(Users.class));

        assertEquals(2, result.size());
        assertEquals("folder1", result.get(0).getName());
        assertEquals("folder2", result.get(1).getName());
    }

    @Test
    public void addFolderTest() {
        // given
        Users user = Users.builder().id(1L).build();

        FolderAddRequestDto dto = FolderAddRequestDto.builder()
                .userId(1L)
                .name("name")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(folderRepository.save(any(Folder.class))).thenAnswer(i -> i.getArgument(0));

        // when
        folderService.addFolder(dto);

        // then
        verify(userRepository, times(1)).findById(anyLong());
        verify(folderRepository, times(1)).save(any(Folder.class));
    }

}
