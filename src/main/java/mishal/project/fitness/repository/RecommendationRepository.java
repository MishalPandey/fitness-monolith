package mishal.project.fitness.repository;

import mishal.project.fitness.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, String> {
    List<Recommendation> findByUsersId(String usersId);

    List<Recommendation> findByActivityId(String activityId);
}
