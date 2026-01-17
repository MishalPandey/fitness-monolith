package mishal.project.fitness.service;

import lombok.RequiredArgsConstructor;
import mishal.project.fitness.dto.RecommendationRequest;
import mishal.project.fitness.model.Activity;
import mishal.project.fitness.model.Recommendation;
import mishal.project.fitness.model.Users;
import mishal.project.fitness.repository.ActivityRepository;
import mishal.project.fitness.repository.RecommendationRepository;
import mishal.project.fitness.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ActivityRepository activityRepository;
    private final UsersRepository usersRepository;
    private final RecommendationRepository recommendationRepository;

    public Recommendation generateRecommendation(RecommendationRequest request) {

        Users user = usersRepository.findById(request.getUsersId())
                .orElseThrow(() -> new RuntimeException("User not Found: "+ request.getUsersId()));

        Activity activity= activityRepository.findById(request.getActivityId())
                .orElseThrow(() -> new RuntimeException("Activity not Found: "+ request.getActivityId()));

        Recommendation recommendation = Recommendation.builder()
                .users(user)
                .activity(activity)
                .improvements(request.getImprovements())
                .suggestions(request.getSuggestions())
                .safety(request.getSafety())
                .build();

        return recommendationRepository.save(recommendation);

    }

    public List<Recommendation> getUsersRecommendation(String usersId) {

        return recommendationRepository.findByUsersId(usersId);
    }

    public List<Recommendation> getactivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId);
    }
}
