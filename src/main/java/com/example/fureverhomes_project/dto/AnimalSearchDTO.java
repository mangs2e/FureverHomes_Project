package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.enumClass.Region;
import com.example.fureverhomes_project.entity.enumClass.Sex;
import com.example.fureverhomes_project.entity.enumClass.Species;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AnimalSearchDTO {
    private Species species; //종
    private Sex sex; //성별
    private Region region; //지역
}
