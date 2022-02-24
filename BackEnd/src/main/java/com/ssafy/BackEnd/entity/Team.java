package com.ssafy.BackEnd.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="team")
@RequiredArgsConstructor
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Team extends CreateTimeEntity {

    @Id
    @GeneratedValue
    @NotNull
    long team_id;

    @NotBlank
    String title;

    String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL)
    @JsonIgnore
    List<TeamKeyword> team_keyword = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "team")
    @JsonIgnore
    List<TeamMember> team_member = new ArrayList<>(); //타입을 TeamMember에서 User로 바꿈

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL)
    @JsonIgnore
    List<TeamFeed> team_feed = new ArrayList<>();

//    @CreatedDate
//    LocalDateTime create_time;

    @Builder
    public Team(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
