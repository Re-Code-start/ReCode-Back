package com.example.recode.dto.problem;

import com.example.recode.domain.Challenge;
import com.example.recode.domain.Difficulty;
import com.example.recode.domain.Problem;
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
public class ProblemAddRequestDto {

    @NotNull
    private Long challengeId;

    @NotBlank
    private String title;

    @NotBlank
    private String link;

    @NotBlank
    private String category;

    @NotNull
    private Difficulty difficulty;

    public Problem toEntity(Challenge challenge, Users user) {
        return Problem.builder()
                .title(title)
                .link(link)
                .category(category)
                .difficulty(difficulty)
                .user(user)
                .challenge(challenge)
                .build();
    }

}
