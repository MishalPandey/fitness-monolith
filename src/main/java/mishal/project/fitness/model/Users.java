package mishal.project.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
//Without using Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;
    private String firstName;
    private String lastName;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    // cascade = CascadeType.ALL means if user is deleted, all activities related to that user will also be deleted
    // orphanRemoval = true means if an activity is removed from the user's activity list, it will be deleted from the database
    @JsonIgnore
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Recommendation> recommendations = new ArrayList<>();
}
