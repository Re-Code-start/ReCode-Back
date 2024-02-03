package com.example.recode.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;            // 챌린지명

    private LocalDateTime startDt;  // 챌린지시작일시

    private LocalDateTime endDt;    // 챌린지마감일시

    @CreatedDate
    private LocalDateTime createDt; // 챌린지생성일시

    private double participation;   // 참여도

    private boolean feedbackVoteYN;     // 피드백 & 투표 가능여부

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<Problem> problems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public void closeFeedbackVote() {
        this.feedbackVoteYN = false;
    }
}
