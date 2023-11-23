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
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;            // 챌린지명

    private LocalDateTime startDt;  // 챌린지시작일시

    private LocalDateTime endDt;    // 챌린지마감일시

    private LocalDateTime createDt; // 챌린지생성일시

    private double participation;   // 참여도

    private boolean feedbackYn;     // 피드백 가능여부

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<Problem> problems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
}
