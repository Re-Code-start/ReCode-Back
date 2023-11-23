package com.example.recode.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;            // 코드

    private String comment;         // 문제접근법

    private boolean noteYn;         // 오답노트삭제여부

    private LocalDateTime createDt; // 생성일시

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;
}
