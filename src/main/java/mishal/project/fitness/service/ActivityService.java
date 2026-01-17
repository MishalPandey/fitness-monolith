package mishal.project.fitness.service;

import lombok.RequiredArgsConstructor;
import mishal.project.fitness.dto.ActivityRequest;
import mishal.project.fitness.dto.ActivityResponse;
import mishal.project.fitness.model.Activity;
import mishal.project.fitness.model.Users;
import mishal.project.fitness.repository.ActivityRepository;
import mishal.project.fitness.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UsersRepository usersRepository;

    public ActivityResponse trackActivity(ActivityRequest request) {

        Users user = usersRepository.findById(request.getUsersId())
                .orElseThrow(() -> new RuntimeException("Invalid user: "+ request.getUsersId()));

        Activity activity = Activity.builder()
                .users(user)
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
        Activity savedActivity = activityRepository.save(activity);

        return mapToTesponse(savedActivity);
    }

    private ActivityResponse mapToTesponse(Activity activity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUsersId(activity.getUsers().getId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }

    public List<ActivityResponse> getUserActivities(String usersId) {

        List<Activity> activityList = activityRepository.findByUsersId(usersId);

        return activityList.stream()
                .map(this::mapToTesponse)
                .collect(Collectors.toList());

    }
}
