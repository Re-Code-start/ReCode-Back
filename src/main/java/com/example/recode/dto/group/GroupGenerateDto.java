package com.example.recode.dto.group;

import com.example.recode.domain.Group;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GroupGenerateDto {
    @NotBlank(message = "그룹 이름을 입력해주세요.")
    private String groupName;

    @NotBlank(message = "그룹 소개글을 입력해주세요.")
    private String groupInfo;

    @NotNull(message = "최대 인원을 입력해주세요.")
    @Max(value = 10, message = "최대 인원은 10명을 넘을 수 없습니다.")
    private Integer maxUsers;

    @Builder
    public Group toEntity() {
        return Group.builder()
                .name(groupName)
                .intro(groupInfo)
                .maxUser(maxUsers)
                .currentUsers(0)
                .build();
    }
}
