package com.ssafy.BackEnd.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HashTagAlgorithm {
    public static List<String> strList(String content){

        List<String> strlist = new ArrayList<>();
        String[] strArr = content.split("#| ");
        for (String s : strArr){
            if(s.equals("")) continue;
            System.out.println("해시태그 #"+s);
            strlist.add(s);
        }
        return strlist;
    }
}
