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
@Table(name = "profilekeyword")
@RequiredArgsConstructor
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class ProfileKeyword {

    @Id @GeneratedValue
    private Long profilekeyword_id;

    int count;

    String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "profile_id")
    @JsonIgnore
    private Profile profile;

    @Builder
    public ProfileKeyword(int count, String name, Profile profile) {
        this.count = count;
        this.name = name;
        this.profile = profile;
    }
}
