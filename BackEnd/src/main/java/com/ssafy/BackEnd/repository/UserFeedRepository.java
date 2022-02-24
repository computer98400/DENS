package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.UserFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeedRepository extends JpaRepository<UserFeed, Long> {
    @Query("select u from UserFeed u where userfeed_id = :userfeed_id")
    UserFeed findByFeedId(long userfeed_id);
}
