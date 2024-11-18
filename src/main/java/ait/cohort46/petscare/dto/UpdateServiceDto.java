package ait.cohort46.petscare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateServiceDto {
    private String title;
    private String description;
    private Double price;
    private String photo;
}