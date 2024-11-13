package ait.cohort46.petscare.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    private int id;
    //todo user_id
    private String title;
    private String description;
    private double price;
    private String photo;
    private ServiceCategory serviceCategory;
}
