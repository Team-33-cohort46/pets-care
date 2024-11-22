package ait.cohort46.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String firstName;

    @Setter
    @Column(nullable = false)
    private String lastName;

    @Setter@Column(unique = true, nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    private String photo;

    @Setter
    private String description;

    @Setter
    private Boolean isDeleted = false;

    @ElementCollection
    @CollectionTable(name = "user_reviews", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "review")
    private List<String> reviews;

    public boolean addReview(String review) {
        return reviews.add(review);
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    //    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
//    @Column(name = "roles")
//    private Set<String> roles = new HashSet<>();
//
//
//    public User() {
//        roles.add("USER");
//    }
}
