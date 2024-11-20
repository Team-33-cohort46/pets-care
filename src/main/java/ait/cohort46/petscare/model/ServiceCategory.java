package ait.cohort46.petscare.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ServiceCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Setter
    @Column(nullable = false)
    private String title;
}
