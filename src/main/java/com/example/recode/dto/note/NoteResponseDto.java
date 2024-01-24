package com.example.recode.dto.note;

import com.example.recode.domain.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDto {

    private String title;

    private String link;

    private FeedbackType feedbackType;

    private String oldCode;

    private String newCode;

    private String improvement;

    private String comment;

}
