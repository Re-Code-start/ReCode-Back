package com.example.recode.dto.feedback;

import com.example.recode.domain.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentFeedbackListDto {

    private Long id;

    private String content;

    private String writerNickname;

    private String writerImageUrl;

    private LocalDateTime createDt;

    public CommentFeedbackListDto(Feedback feedback) {
        this.id = feedback.getId();
        this.content = feedback.getContent();
        this.writerNickname = feedback.getUser().getNickname();
        this.writerImageUrl = feedback.getUser().getImageUrl();
        this.createDt = feedback.getCreateDt();
    }

}
