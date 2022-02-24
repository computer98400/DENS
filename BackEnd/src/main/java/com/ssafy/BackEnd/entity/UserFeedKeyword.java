package com.ssafy.BackEnd.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "userfeedkeyword")
@RequiredArgsConstructor
@Getter @Setter
public class UserFeedKeyword {

    @Id @GeneratedValue
    long userfeedkeyword_id;

    int count;

    String name;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
//    @JoinColumn(name = "keyword_id")
//    Keyword keyword;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userfeed_id")
    UserFeed user_feed;
}
