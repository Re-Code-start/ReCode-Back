package com.example.recode.dto.group;

import com.example.recode.domain.User_Group;
import com.example.recode.domain.GroupMemberStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupMemberDto {
    private Long memberId;
    private LocalDateTime visitDt;  // 그룹원 방문일시
    private LocalDateTime joinDt;   // 그룹 가입일시
    private GroupMemberStatus status;    // 그룹원 상태
    private String nickname;
    private String imageUrl;

    public GroupMemberDto(User_Group group) {
        this.memberId = group.getGroupMember().getId();
        this.nickname = group.getGroupMember().getNickname();
        this.imageUrl = group.getGroupMember().getImageUrl();
        this.visitDt = group.getVisitDt();
        this.joinDt = group.getJoinDt();
        this.status = group.getStatus();
    }

}
