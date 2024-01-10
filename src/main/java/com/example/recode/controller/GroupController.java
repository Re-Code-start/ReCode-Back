package com.example.recode.controller;

import com.example.recode.domain.Group;
import com.example.recode.dto.group.GroupGenerateDto;
import com.example.recode.dto.group.GroupJoinDto;
import com.example.recode.dto.group.GroupMemberDto;
import com.example.recode.dto.group.GroupReturnDto;
import com.example.recode.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    // 그룹방 만들기
    @PostMapping("/make")
    public ResponseEntity<Boolean> makeGroup(@Valid @RequestBody GroupGenerateDto group) {
        return ResponseEntity.ok(groupService.generateGroup(group));
    }

    // 그룹방 가입
    @PostMapping("/join/{groupId}")
    public ResponseEntity<GroupJoinDto> join(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(groupService.applyGroup(groupId));
    }

    // 그룹방 가입 신청 승인
    @PatchMapping("/join/{groupId}")
    public ResponseEntity<Boolean> approve(@PathVariable("groupId") Long groupId, @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(groupService.approveMember(userId, groupId));
    }

    // 그룹방 가입 신청 거부
    @PatchMapping("/join/reject/{groupId}")
    public ResponseEntity<Boolean> reject(@PathVariable("groupId") Long groupId, @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(groupService.rejectMember(userId, groupId));
    }

    // 내 그룹방 조회
    @GetMapping("/my")
    public ResponseEntity<List<GroupReturnDto>> getMyGroups() {
        return ResponseEntity.ok(groupService.getMyGroups());
    }

    // 특정 그룹방 멤버 현황 조회
    @GetMapping("/{groupId}")
    public  ResponseEntity<List<GroupMemberDto>> getGroupMembers(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(groupService.getGroupMembers(groupId));
    }

    // 특정 그룹방 멤버 현황 조회
    @GetMapping("/rank")
    public  ResponseEntity<List<GroupReturnDto>> getGroupRanking() {
        return ResponseEntity.ok(groupService.getGroupRanking());
    }

    // 모든 그룹방 조회
    @GetMapping("/")
    public ResponseEntity<List<GroupReturnDto>> getGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass().getSimpleName() + ": " + e.getMessage());
    }
}
