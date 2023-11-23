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
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;    // 그룹명

    private String intro;   // 그룹 설명

    private int maxUser;    // 최대인원수

    private double participation;   // 참여도

    private String imageUrl;        // 그룹이미지URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupLeader_id")
    private User groupLeader;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<User_Group> user_groups = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Challenge> challenges = new ArrayList<>();

}
