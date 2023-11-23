package com.example.recode.domain;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;           // 제목

    private String link;            // 문제링크

    @Enumerated(EnumType.STRING)
    private FeedbackType type;      // 피드백 타입

    private String oldCode;         // 기존 코드

    private String newCode;         // 개선 코드

    private String improvement;     // 개선점

    private String comment;         // 문제 접근법

    private LocalDateTime createDt; // 생성 일시

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

}
