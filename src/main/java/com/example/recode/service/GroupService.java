package com.example.recode.service;

import com.example.recode.domain.Users;
import com.example.recode.dto.group.GroupGenerateDto;
import com.example.recode.dto.group.GroupJoinDto;
import com.example.recode.dto.group.GroupMemberDto;
import com.example.recode.dto.group.GroupReturnDto;

import java.util.List;

public interface GroupService {
    boolean generateGroup(GroupGenerateDto request);
    boolean approveMember(Long userId, Long groupId);
    boolean rejectMember(Long userId, Long groupId);
    GroupJoinDto applyGroup(Long groupId);
    List<GroupReturnDto> getAllGroups();
    List<GroupReturnDto> getMyGroups();
    Users findCurrentUser();

    List<GroupMemberDto> getGroupMembers(Long groupId);

    List<GroupReturnDto> getGroupRanking();
}
