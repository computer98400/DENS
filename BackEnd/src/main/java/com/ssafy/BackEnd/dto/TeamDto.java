package com.ssafy.BackEnd.dto;

import com.ssafy.BackEnd.entity.Team;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class TeamDto {

    @NotBlank
    private String title;

    private String content;


    @Builder
    public TeamDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
    public Team createTeam() {
            return Team.builder()
                    .title(title)
                    .content(content)
                    .build();
    }
}
