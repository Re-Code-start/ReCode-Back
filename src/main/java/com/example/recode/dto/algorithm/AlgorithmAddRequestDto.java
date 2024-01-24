package com.example.recode.dto.algorithm;

import com.example.recode.domain.Folder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlgorithmAddRequestDto {

    private Long folderId;

    @NotBlank
    private String algorithmName;
/*
    public Algorithm toEntity(Folder folder) {
        return Algorithm.builder()
                .name(algorithmName)
                .folder(folder)
                .build();
    }

 */

}
