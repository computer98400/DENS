package com.ssafy.BackEnd.dto;

import com.ssafy.BackEnd.entity.Team;
import com.ssafy.BackEnd.entity.TeamMember;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamMemberDto {
    private String email;
    private String teamName;

    @Builder
    public TeamMemberDto(String email) {
        this.email = email;
        //this.teamName = teamName;
    }

//    public TeamMember createTeamMmber() {
//        return TeamMember.builder()
//                .email(email)
//                .teamName(teamName)
//                .build();
//    }
}
