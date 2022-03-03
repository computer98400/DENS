package com.ssafy.BackEnd.service.profile;


import com.ssafy.BackEnd.entity.Profile;
import com.ssafy.BackEnd.repository.ImageRepository;
import com.ssafy.BackEnd.repository.ProfileRepository;
import com.ssafy.BackEnd.service.file.FileHandler;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private final ImageRepository imageRepository;

    @Autowired
    private final ProfileRepository profileRepository;

    @Autowired
    private final FileHandler fileHandler;

    @Value("${profileImg.path}")
    private String uploadFolder;

    public ImageService(ImageRepository imageRepository, ProfileRepository profileRepository, FileHandler fileHandler) {
        this.imageRepository = imageRepository;
        this.profileRepository = profileRepository;
        this.fileHandler = fileHandler;
    }

    // 주소 같이 넘겨주기
    @Transactional
    public String update(Long profile_id, MultipartFile multipartFile) throws NotFoundException{
        Optional<Profile> findProfile = profileRepository.findById(profile_id);
        if (findProfile == null) throw new NotFoundException("프로필을 조회하지 못했습니다.");
        Profile profile = findProfile.get();
        String imageFileName = profile_id + "_" + multipartFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        if(multipartFile.getSize() != 0) {
            try {
                if (profile.getImage() != null) {
                    File file = new File(uploadFolder + profile.getImage());
                    file.delete();
                }
                Files.write(imageFilePath, multipartFile.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
            profile.setImage(imageFileName);
        }
        return imageFilePath.toString();

    }

}
