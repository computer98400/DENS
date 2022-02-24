package com.ssafy.BackEnd.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Table(name = "teamfeedfile")
@Getter @Setter
public class TeamFeedFile {

    @Id @GeneratedValue
    long teamfeedfile_id;

    private String originalFileName;

    private String fileName;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "teamfeed_id")
    TeamFeed team_feed;

    @Builder
    public TeamFeedFile(String originalFileName, String storePath, FileType fileType) {
        this.originalFileName = originalFileName;
        this.fileName = storePath;
        this.fileType = fileType;
    }
}
