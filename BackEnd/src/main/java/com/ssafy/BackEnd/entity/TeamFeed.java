package com.ssafy.BackEnd.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teamfeed")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class TeamFeed extends BaseTimeEntity{

    @Id @GeneratedValue
    long teamfeed_id;

    String content;

//    @CreatedDate
//    LocalDateTime create_time;
//
//    @LastModifiedDate
//    LocalDateTime modify_time;

    String writer;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonIgnore
    Team team;

    @OneToMany(mappedBy = "team_feed", cascade = CascadeType.ALL) // many로 끝날때 fetch nono
    @Nullable
    List<TeamFeedFile> teamFeedFiles = new ArrayList<>();

    @OneToMany(mappedBy = "team_feed", cascade = CascadeType.ALL)
    List<TeamFeedKeyword> teamFeedKeywords = new ArrayList<>();

    @Builder
    public TeamFeed(Team team, String content, List<TeamFeedFile> teamFeedFiles) {
        this.team = team;
        this.content = content;
        this.teamFeedFiles = teamFeedFiles;
    }
}
