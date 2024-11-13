package ait.cohort46.petscare.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCategory {
    private int id;
    @Setter
    private String title;
}
