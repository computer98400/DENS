package com.ssafy.BackEnd.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Table(name = "teamkeyword")
@Getter @Setter
public class TeamKeyword {
    @Id
    @GeneratedValue
    @Column(name="teamkeyword_id")
    long teamkeyword_id;

    int count;

    String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_id")
    Team team;

    @Builder
    public TeamKeyword(int count, String name, Team team) {
        this.count = count;
        this.name = name;
        this.team = team;
    }
}
