package ait.cohort46.pet.model;

import ait.cohort46.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(nullable = false)
    @NotBlank
    private String name;
    @Column(nullable = false)
    @NotBlank
    private String type;
    private String photo;
    @ManyToOne
    private User user;
    private boolean isDeleted = false;

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
