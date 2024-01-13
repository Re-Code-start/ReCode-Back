package com.example.recode.dto.group;

import com.example.recode.domain.Group;
import lombok.Data;

@Data
public class GroupReturnDto {
    private Long id;
    private String name;
    private String intro;
    private int maxUser;
    private int currentUsers;
    private double participation;
    private String imageUrl;

    public GroupReturnDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.intro = group.getIntro();
        this.maxUser = group.getMaxUser();
        this.currentUsers = group.getCurrentUsers();
        this.participation = group.getParticipation();
        this.imageUrl = group.getImageUrl();
    }
}
