package com.example.recode.dto.folder;

import com.example.recode.domain.Folder;
import com.example.recode.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderAddRequestDto {

    @NotBlank
    private String userId;  // 폴더 생성자 ID

    @NotBlank
    private String name;    // 폴더명

    public Folder toEntity(Users user) {
        return Folder.builder()
                .name(name)
                .user(user)
                .build();
    }

}
