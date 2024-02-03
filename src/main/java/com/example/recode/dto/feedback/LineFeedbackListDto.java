package com.example.recode.dto.feedback;

import com.example.recode.domain.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineFeedbackListDto {

    private Long id;

    private int lineNum;

    private String content;

    private String writerNickname;

    private String writerImageUrl;

    public LineFeedbackListDto(Feedback feedback) {
        this.id = feedback.getId();
        this.lineNum = feedback.getLineNum();
        this.content = feedback.getContent();
        this.writerNickname = feedback.getUser().getNickname();
        this.writerImageUrl = feedback.getUser().getImageUrl();
    }

}
