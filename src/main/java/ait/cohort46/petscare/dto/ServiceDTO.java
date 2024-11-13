package ait.cohort46.petscare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
    private Integer id;
    //TODO userId
    private Integer userId;
    private String title;
    private String description;
    private Double price;
    private String photo;
    private ServiceCategoryDTO serviceCategoryId;
}
