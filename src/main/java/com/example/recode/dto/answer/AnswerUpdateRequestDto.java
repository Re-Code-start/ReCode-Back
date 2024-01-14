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
public class AnswerUpdateRequestDto {

    private String code;

    private String comment;

    private List<Long> algorithmIds;

}
