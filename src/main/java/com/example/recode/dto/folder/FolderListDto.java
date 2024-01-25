package com.example.recode.dto.folder;

import com.example.recode.domain.FolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderListDto {

    private Long id;

    private String name;

    private FolderType folderType;

}
