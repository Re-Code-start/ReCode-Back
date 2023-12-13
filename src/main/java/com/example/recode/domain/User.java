package com.example.recode.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;

@Builder
@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String password;    // 비밀번호

    private String nickname;    // 닉네임

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MembershipLevel membershipLevel = MembershipLevel.BASIC;    // 멤버쉽 등급

    private String imageUrl;    // 프로필이미지URL

    @ColumnDefault("0")
    private int heart;          // 받은 하트 수

    private String email;

    @OneToMany(mappedBy = "groupLeader", cascade = CascadeType.ALL)
    private List<Group> groups = new ArrayList<>();

    @OneToMany(mappedBy = "groupMember", cascade = CascadeType.ALL)
    private List<User_Group> user_groups = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Algorithm> algorithms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Problem> problems = new ArrayList<>();

    @OneToMany(mappedBy = "bestCoder", cascade = CascadeType.ALL)
    private List<Problem> bestCodeProblems = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "voter", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

}
