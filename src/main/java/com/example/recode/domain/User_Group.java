package com.example.recode.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.ConnectionBuilder;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User_Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime visitDt;  // 그룹원 방문일시

    private LocalDateTime joinDt;   // 그룹 가입일시

    @Enumerated(EnumType.STRING)
    private GroupMemberStatus status;    // 그룹원 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupMember_id")
    private Users groupMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

}
