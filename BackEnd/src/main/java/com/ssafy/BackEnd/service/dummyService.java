package com.ssafy.BackEnd.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ssafy.BackEnd.entity.dummy;
import com.ssafy.BackEnd.repository.dummyRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class dummyService {

    @Autowired
    dummyRepository test;

    


    @Transactional
    public List<dummy> searchTestCheck(String keyword) {
        List<dummy> result = test.findByNameContaining(keyword);
        System.out.println("key : "+keyword);
        System.out.println("size : "+result.size());
        return result;
    }
}
