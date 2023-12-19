package com.example.recode.domain;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private Users member;

    public SecurityUser(Users member) {
        super(member.getId().toString(), member.getPassword(),
                AuthorityUtils.createAuthorityList(member.getNickname().toString()));
        this.member = member;
    }
}
