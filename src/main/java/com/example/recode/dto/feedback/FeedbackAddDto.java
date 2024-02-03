package com.example.recode.dto.feedback;

import com.example.recode.domain.Answer;
import com.example.recode.domain.Feedback;
import com.example.recode.domain.FeedbackType;
import com.example.recode.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAddDto {

    @NotNull
    private Long referenceId;          // 질문이나 풀이 코드 ID

    private Integer lineNum;

    @NotBlank
    private String content;

    public Feedback toEntityWithAnswer(Users user, Answer answer) {
        return Feedback.builder()
                .lineNum(lineNum)
                .content(content)
                .type(lineNum != null ? FeedbackType.LINE_FEEDBACK : FeedbackType.COMMENT_FEEDBACK)
                .user(user)
                .answer(answer)
                .build();
    }

}
