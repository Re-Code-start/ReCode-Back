package com.example.recode.dto.problem;

import com.example.recode.domain.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemListDto {

    private Long id;

    private String title;

    private String category;

    private Difficulty difficulty;

    private Long bestCoderId;

    private String bestCoderImageUrl;

    private boolean userAnswerYN;

    private int solvedMemberCount;

    private int totalMemberCount;

}
