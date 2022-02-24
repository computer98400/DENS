package com.ssafy.BackEnd.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile")
@RequiredArgsConstructor
@Getter @Setter
public class Profile implements Serializable {

    private static final long serialVersionUID = 6494678977089006637L;

    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long profile_id;

    private String name;

    private String position;

    private String stack;

    private String email;

    private String image;

    private String git;

    private String git_id;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @JsonIgnore
    List<UserFeed> user_feed = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
//    @JsonIgnore
    List<ProfileKeyword> profile_keyword = new ArrayList<>();

    @Builder
    public Profile(String name, String position, String stack, String email, String image, String git, String git_id, LocalDateTime createDate) {
        this.name = name;
        this.position = position;
        this.stack = stack;
        this.email = email;
        this.image = image;
        this.git = git;
        this.git_id = git_id;
        this.createDate = createDate;
    }
}
