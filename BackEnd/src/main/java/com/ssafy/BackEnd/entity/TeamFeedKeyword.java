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
@RequiredArgsConstructor
@Table(name = "teamfeedkeyword")
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class TeamFeedKeyword {

    @Id @GeneratedValue
    long teamfeedkeyword_id;

    String name;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "teamfeed_id")
    @JsonIgnore
    TeamFeed team_feed;

    @Builder
    public TeamFeedKeyword(String name, TeamFeed teamFeed) {
        this.name = name;
        this.team_feed = teamFeed;
    }
}
