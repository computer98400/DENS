package com.ssafy.BackEnd.dto;

import com.ssafy.BackEnd.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Builder
@Getter @Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
@ApiModel(value = "UserIdDto", description = "사용자 아이디 (pkey)")
public class UserIdDto implements Serializable {

    @ApiModelProperty(value = "아이디")
    private long id;

    @ApiModelProperty(value = "이름")
    private String name;

}