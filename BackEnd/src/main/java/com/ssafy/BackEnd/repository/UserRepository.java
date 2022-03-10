package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where email = :email and password = :password")
    User findUser(String email, String password);

    User findByEmail (String Email);

    User findByName (String name);

    @Query(value = "select u from User u where profile_id = :profile_id")
    User findByProfileId (Long profile_id);
}
