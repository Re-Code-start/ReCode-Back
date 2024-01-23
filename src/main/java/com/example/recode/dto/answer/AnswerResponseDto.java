package com.example.recode.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponseDto {

    private String title;

    private String link;

    private String code;

    private String comment;

    private List<String> algorithmList;

}
