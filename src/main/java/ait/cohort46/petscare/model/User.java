package ait.cohort46.petscare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private int user_id;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String email;
    @Setter
    private String password;
}
