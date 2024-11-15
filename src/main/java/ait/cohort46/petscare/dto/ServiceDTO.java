package ait.cohort46.petscare.dto;

import ait.cohort46.user.dto.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
    private Integer id;
    private UserRequestDto user;
    private String title;
    private String description;
    private Double price;
    private String photo;
    private ServiceCategoryDTO serviceCategory;
}
