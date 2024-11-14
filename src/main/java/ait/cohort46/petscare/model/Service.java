package ait.cohort46.petscare.model;

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
    private int id;

    //TODO user_id
    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private double price;

    //private String photo;

    @ManyToOne
    private ServiceCategory serviceCategory;
}
