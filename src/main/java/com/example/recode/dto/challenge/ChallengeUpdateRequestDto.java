package com.example.recode.dto.challenge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeUpdateRequestDto {

    private LocalDateTime startDt;  // 챌린지시작일시

    private LocalDateTime endDt;    // 챌린지마감일시

}
