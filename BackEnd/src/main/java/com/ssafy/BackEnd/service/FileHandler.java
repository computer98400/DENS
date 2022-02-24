package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.entity.Image;
import freemarker.template.SimpleDate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileHandler {

    public Image parseFileInfo(Long profileId, MultipartFile multipartFile) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        String absolutePath = new File("").getAbsolutePath() + "\\";

        String path = "images/" + current_date;
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        String contentType = multipartFile.getContentType();

        String originalFileExtension = new String();

        if (!ObjectUtils.isEmpty(contentType)) {
            if (contentType.contains("image/jpeg")) {
                originalFileExtension = ".jpg";
            } else if (contentType.contains("image/png")) {
                originalFileExtension = ".png";
            } else if (contentType.contains("image/gif")) {
                originalFileExtension = ".gif";
            }
        } else throw new Exception("올바른 확장자가 아닙니다.");

        String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;

        Image image = Image.builder()
                .profileId(profileId)
                .original_file_name(multipartFile.getOriginalFilename())
                .stored_file_path(path + "/" + new_file_name)
                .file_size(multipartFile.getSize())
                .build();

        file = new File(absolutePath + path + "/" + new_file_name);
        multipartFile.transferTo(file);

        return image;
    }
    }

