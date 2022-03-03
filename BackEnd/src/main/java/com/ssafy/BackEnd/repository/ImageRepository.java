package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Integer> {
    Image save(Image image);

    List<Image> findAllByProfileId(Integer profileId);
}