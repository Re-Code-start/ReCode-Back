package com.example.recode.dto.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolvedMemberListDto {

    private Long answerId;

    private Long userId;

    private String userImageUrl;

    private List<String> algorithmList;

}
