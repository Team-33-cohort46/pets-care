package ait.cohort46.petscare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewServiceDto {
    private String title;
    private String description;
    private Double price;
    private Integer serviceCategory;
}
