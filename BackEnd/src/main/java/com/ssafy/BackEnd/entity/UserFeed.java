package com.ssafy.BackEnd.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "userfeed")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class UserFeed extends BaseTimeEntity{

    @Id
    @GeneratedValue
    long userfeed_id;

    String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    Profile profile;

    @OneToMany(mappedBy = "user_feed", cascade = CascadeType.ALL)
    List<UserFeedFile> userFeedFiles = new ArrayList<>();

    @OneToMany(mappedBy = "user_feed", cascade = CascadeType.MERGE)
    List<UserFeedKeyword> userfeed_keyword = new ArrayList<>();

    @Builder
    public UserFeed(Profile profile, String content, List<UserFeedFile> userFeedFiles) {
        this.profile = profile;
        this.content = content;
        this.userFeedFiles = userFeedFiles;
    }

}
