package ait.cohort46.petscare.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Pet {
    @Id
    private int pet_id;
    private int user_id;
    private String type;
    @Setter
    private String name;
    @Setter
    private String photo;
}
