package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.TeamFeedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamFeedFileRepository extends JpaRepository<TeamFeedFile, Long> {

    TeamFeedFile findByFileName(String filename);

}
