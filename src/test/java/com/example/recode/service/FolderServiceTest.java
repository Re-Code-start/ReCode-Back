package com.example.recode.service;

import com.example.recode.domain.Folder;
import com.example.recode.repository.FolderRepository;
import com.example.recode.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
