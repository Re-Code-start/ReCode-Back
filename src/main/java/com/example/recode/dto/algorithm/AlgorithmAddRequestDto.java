package com.example.recode.dto.algorithm;

import com.example.recode.domain.Algorithm;
import com.example.recode.domain.Folder;
import com.example.recode.domain.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlgorithmAddRequestDto {

    private Long folderId;

    private Long noteId;

    private String algorithmName;

    public Algorithm toEntity(Folder folder, Note note) {
        return Algorithm.builder()
                .name(algorithmName)
                .folder(folder)
                .note(note)
                .build();
    }

}
