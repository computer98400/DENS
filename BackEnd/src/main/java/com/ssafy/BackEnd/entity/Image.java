package com.ssafy.BackEnd.entity;//package com.ssafy.BackEnd.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "image")
//@Getter @Setter
//
//public class Image {
//
//    @Id @GeneratedValue
//    Long image_id;
//
//    @Column(nullable = false)
//    String orig_image_name;
//
//    @Column(nullable = false)
//    String image_name;
//
//    @Column(nullable = false)
//    String savefolder;
//
//    @Builder
//    public Image(Long image_id, String orig_image_name, String image_name, String savefolder) {
//        this.image_id = image_id;
//        this.orig_image_name = orig_image_name;
//        this.image_name = image_name;
//        this.savefolder = savefolder;
//    }
//
//    public Image(String savefolder) {
//        this.savefolder = savefolder;
//    }
//}

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private Long profileId;

    // 원본 파일이름 과 서버에 저장된 파일 경로 를 분리한 이유?
    // 동일한 이름을 가진 파일이 업로드가 된다면 오류가 생긴다.
    // 이를 해결하기 위함
    @NotEmpty
    private String original_file_name;
    @NotEmpty
    private String stored_file_path;

    private long file_size;
}
