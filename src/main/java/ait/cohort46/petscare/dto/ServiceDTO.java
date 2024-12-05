package ait.cohort46.petscare.dto;

import ait.cohort46.user.dto.UserRequestDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceDTO {
    private Long id;
    private UserRequestDto user;
    private String title;
    private String description;
    private Double price;
    private String photo;
    private ServiceCategoryDTO serviceCategory;
}
