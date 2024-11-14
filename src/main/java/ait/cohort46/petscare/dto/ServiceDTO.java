package ait.cohort46.petscare.dto;

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
    //TODO userId
    private Long userId;
    private String title;
    private String description;
    private Double price;
    //private String photo;
    private Integer serviceCategoryId;
}
