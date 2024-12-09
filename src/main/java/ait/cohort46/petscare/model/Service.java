package ait.cohort46.petscare.model;

import ait.cohort46.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Service implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(length = 500, nullable = true)
    private String description;

    @Column(nullable = false)
    private double price;

    // private String photo;

    @ManyToOne
    private ServiceCategory serviceCategory;
}
