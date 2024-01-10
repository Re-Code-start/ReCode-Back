package com.example.recode.repository;

import com.example.recode.domain.Group;
import com.example.recode.domain.User_Group;
import com.example.recode.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface User_GroupRepository extends JpaRepository<User_Group, Long> {
    List<User_Group> findAllByGroupMember(Users groupMember);

    User_Group findByGroupMemberAndGroup(Users groupMember, Group group);
}
