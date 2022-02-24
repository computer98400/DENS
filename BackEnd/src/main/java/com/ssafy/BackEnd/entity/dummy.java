package com.ssafy.BackEnd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;


@Entity
@RequiredArgsConstructor
@Table(name = "dummytable")
@Getter @Setter
public class dummy {
    
    @Id @GeneratedValue
    int id;

    String name;
}
