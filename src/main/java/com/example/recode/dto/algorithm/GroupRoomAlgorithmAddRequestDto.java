package com.example.recode.dto.algorithm;

import com.example.recode.domain.Algorithm;
import com.example.recode.domain.Folder;
import com.example.recode.domain.Group;
import com.example.recode.domain.User_Group;
import com.example.recode.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupRoomAlgorithmAddRequestDto {

    @NotNull
    private Long groupId;

    @NotNull
    private Long userId;

    @NotBlank
    private String algorithmName;

    public Algorithm toEntity(User_Group userGroup) {
        return Algorithm.builder()
                .name(algorithmName)
                .userGroup(userGroup)
                .build();
    }

}
