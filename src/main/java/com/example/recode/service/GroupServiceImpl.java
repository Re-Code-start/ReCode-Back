package com.example.recode.service;

import com.example.recode.domain.Group;
import com.example.recode.domain.GroupMemberStatus;
import com.example.recode.domain.Users;
import com.example.recode.domain.User_Group;
import com.example.recode.dto.group.GroupGenerateDto;
import com.example.recode.dto.group.GroupJoinDto;
import com.example.recode.dto.group.GroupMemberDto;
import com.example.recode.dto.group.GroupReturnDto;
import com.example.recode.repository.GroupRepository;
import com.example.recode.repository.UserRepository;
import com.example.recode.repository.User_GroupRepository;
import com.example.recode.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final User_GroupRepository user_groupRepository;

    @Override
    public boolean generateGroup(GroupGenerateDto reqDto) {
        if (getMyGroups().size() == 5){
            throw new RuntimeException("참여할 수 있는 그룹방은 최대 5개입니다.");
        }
        Users groupLeader = findCurrentUser();
        Group group = reqDto.toEntity();
        group.setGroupLeader(groupLeader);

        try{
            groupRepository.save(group);
            return true;
        }catch(Exception e){
            throw new RuntimeException("그룹 생성 중 에러가 발생하였습니다.", e);
        }
    }

    @Override
    public boolean approveMember(Long userId, Long groupId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("그룹을 찾을 수 없습니다."));

        User_Group userGroup = user_groupRepository.findByGroupMemberAndGroup(user, group);
        if (userGroup == null){
            throw new RuntimeException("userGroup을 찾을 수 없습니다.");
        }
        if (userGroup.getStatus() != GroupMemberStatus.PENDING) {
            throw new RuntimeException("승인 대기 중인 유저가 아닙니다.");
        } else {
            userGroup.setStatus(GroupMemberStatus.MEMBER);
            userGroup.setJoinDt(LocalDateTime.now());

            user_groupRepository.save(userGroup);
            return true;
        }
    }

    @Override
    public boolean rejectMember(Long userId, Long groupId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("그룹을 찾을 수 없습니다."));

        User_Group userGroup = user_groupRepository.findByGroupMemberAndGroup(user, group);
        if (userGroup == null){
            throw new RuntimeException("userGroup을 찾을 수 없습니다.");
        }
        if (userGroup.getStatus() != GroupMemberStatus.PENDING) {
            throw new RuntimeException("승인 대기 중인 유저가 아닙니다.");
        } else {
            userGroup.setStatus(GroupMemberStatus.REJECTED);

            user_groupRepository.save(userGroup);
            return true;
        }
    }

    @Override
    public GroupJoinDto applyGroup(Long groupId) {
        GroupJoinDto res = new GroupJoinDto();

        if (getMyGroups().size() == 5){
            res.setResult(false);
            res.setMessage("참여할 수 있는 그룹방은 최대 5개입니다.");
            return res;
        }

        Users applicant = findCurrentUser();
        Users user = userRepository.findById(applicant.getId()).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("그룹을 찾을 수 없습니다."));

        User_Group userGroup = user_groupRepository.findByGroupMemberAndGroup(user, group);
        if (userGroup != null) {
            if (userGroup.getStatus() == GroupMemberStatus.KICK) {
                res.setResult(false);
                res.setMessage("방장에 의해 내보내기 된 그룹방입니다.");
                return res;
            }
            throw new RuntimeException("이미 가입된 그룹입니다.");
        }

        User_Group newUserGroup = User_Group.builder()
                .groupMember(user)
                .group(group)
                .status(GroupMemberStatus.PENDING)
                .build();
        user_groupRepository.save(newUserGroup);

        res.setResult(true);
        res.setMessage("가입 신청이 완료되었습니다.");
        return res;
    }

    @Override
    public List<GroupReturnDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(GroupReturnDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupReturnDto> getMyGroups() {
        Users currentUser = findCurrentUser();
        Long userId = currentUser.getId();

        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        List<Group> leaderGroups = user.getGroups();
        List<Group> memberGroups = user_groupRepository.findAllByGroupMember(user).stream()
                .map(User_Group::getGroup)
                .collect(Collectors.toList());

        List<Group> myGroups = new ArrayList<>();
        myGroups.addAll(leaderGroups);
        myGroups.addAll(memberGroups);

        return myGroups.stream()
                .map(GroupReturnDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Users findCurrentUser() {
        String currentUserId = SecurityUtil.getCurrentMemberId();
        return userRepository.findById(Long.valueOf(currentUserId))
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다. : " + currentUserId));
    }

    @Override
    public List<GroupMemberDto> getGroupMembers(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("그룹을 찾을 수 없습니다."));
        List<User_Group> groupMembers = group.getUser_groups();

        return groupMembers.stream()
                .map(GroupMemberDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupReturnDto> getGroupRanking() {
        return groupRepository.findTop5ByOrderByParticipationDesc().stream()
                .map(GroupReturnDto::new)
                .collect(Collectors.toList());
    }
}
