package com.ssafy.BackEnd.repository;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.BackEnd.entity.dummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;




public interface dummyRepository extends JpaRepository<dummy, Integer> {


    List<dummy> findByNameContaining(String keyword);



}
