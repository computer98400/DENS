package com.ssafy.BackEnd.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "teammember")
@RequiredArgsConstructor
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class TeamMember {
    @Id
    @GeneratedValue
    long teammember_id; // 혹시? notblank ??

    @Column(name = "team_identity")
    @Enumerated(EnumType.STRING)
    TeamMemberIdentity team_identity = TeamMemberIdentity.LEADER;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_id")
    Team team;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "email")
    //@JsonIgnore
    User user;

    @Builder
    public TeamMember(Team team, User user, TeamMemberIdentity teamMemberIdentity) {
        this.team = team;
        this.user = user;
        this.team_identity = teamMemberIdentity;
    }

}
