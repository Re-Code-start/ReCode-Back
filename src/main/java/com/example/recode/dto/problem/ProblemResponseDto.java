package com.example.recode.dto.problem;

import com.example.recode.domain.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemResponseDto {

    private Long id;

    private String title;

    private String link;

    private String category;

    private Difficulty difficulty;

    private List<SolvedMemberListDto> solvedMembers;

}
