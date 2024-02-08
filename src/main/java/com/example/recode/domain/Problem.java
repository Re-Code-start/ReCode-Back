package com.example.recode.domain;

import com.example.recode.dto.problem.ProblemUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;           // 문제제목

    private String link;            // 문제링크

    private String category;        // 카테고리

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;  // 난이도

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bestCoder_id")
    private Users bestCoder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    public void updateInfo(ProblemUpdateRequestDto dto) {
        if (dto.getTitle() != null)  this.title = dto.getTitle();
        if (dto.getLink() != null)  this.link = dto.getLink();
        if (dto.getCategory() != null)  this.category = dto.getCategory();
        if (dto.getDifficulty() != null)  this.difficulty = dto.getDifficulty();
    }

}
