package com.example.recode.dto.challenge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeAddRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private LocalDateTime startDt;

    @NotNull
    private LocalDateTime endDt;

    @NotNull
    private Long groupId;

}
