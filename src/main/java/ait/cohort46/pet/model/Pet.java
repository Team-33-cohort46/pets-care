package ait.cohort46.pet.model;

import ait.cohort46.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String photo;
    @ManyToOne
    private User user;
    private boolean isDeleted = false;

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
