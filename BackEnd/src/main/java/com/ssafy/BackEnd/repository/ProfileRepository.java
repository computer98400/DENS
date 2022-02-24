package com.ssafy.BackEnd.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.BackEnd.entity.Profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByName(String name);

    Optional<Profile> findByEmail(String email);
    
    List<Profile> findByNameContaining(String keyword);
}
