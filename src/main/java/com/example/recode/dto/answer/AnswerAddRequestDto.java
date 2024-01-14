package com.example.recode.dto.answer;

import com.example.recode.domain.Answer;
import com.example.recode.domain.Problem;
import com.example.recode.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerAddRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long problemId;

    @NotBlank
    private String code;

    @NotBlank
    private String comment;

    @NotEmpty
    private List<String> algorithmNameList;

    public Answer toEntity(Users user, Problem problem) {
        return Answer.builder()
                .code(code)
                .comment(comment)
                .noteYn(false)
                .user(user)
                .problem(problem)
                .build();
    }
}
