package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.ProfileKeyword;
import com.ssafy.BackEnd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileKeywordRepository extends JpaRepository<ProfileKeyword, Long> {
    @Query(value = "select k from ProfileKeyword k where keyword_id = :keyword_id")
    List<ProfileKeyword> findByKeywordId(Long keyword_id);

    @Query(value = "select k from ProfileKeyword k where profile_id = :profile_id")
    List<ProfileKeyword> findByProfileId(Long profile_id);

    @Query(value = "select k from ProfileKeyword k where name = :name and profile_id = :profile_id")
    ProfileKeyword findProfileKeyword(String name, Long profile_id);

    List<ProfileKeyword> findByNameContaining(String keyword);

}
