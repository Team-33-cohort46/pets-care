package ait.cohort46.petscare.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateServiceDto {
    private String title;
    private String description;
    private Double price;
    private String photo;
}
