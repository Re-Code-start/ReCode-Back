package com.example.recode.dto.answer;

import com.example.recode.dto.feedback.CommentFeedbackListDto;
import com.example.recode.dto.feedback.LineFeedbackListDto;
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

    private String code;

    private String comment;

    private List<String> algorithmList;

    private List<LineFeedbackListDto> lineFeedbacks;

    private List<CommentFeedbackListDto> commentFeedbacks;

}
