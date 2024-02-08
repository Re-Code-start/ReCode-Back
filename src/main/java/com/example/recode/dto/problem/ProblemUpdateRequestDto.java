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
public class ProblemUpdateRequestDto {

    private String title;

    private String link;

    private String category;

    private Difficulty difficulty;

}
