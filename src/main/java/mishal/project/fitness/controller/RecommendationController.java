package mishal.project.fitness.controller;

import lombok.RequiredArgsConstructor;
import mishal.project.fitness.dto.RecommendationRequest;
import mishal.project.fitness.model.Recommendation;
import mishal.project.fitness.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<Recommendation> generateRecommendation(
            @RequestBody RecommendationRequest request
    ){
        Recommendation recommendation = recommendationService.generateRecommendation(request);
        return ResponseEntity.ok(recommendation);
    }

    @GetMapping("/users/{usersId}")
    public ResponseEntity<List<Recommendation>> getUsersRecommendation(
            @PathVariable String usersId
    ){
        List<Recommendation> recommendationList = recommendationService.getUsersRecommendation(usersId);
        return ResponseEntity.ok(recommendationList);
    }
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Recommendation>> getActivityRecommendation(
            @PathVariable String activityId
    ){
        List<Recommendation> recommendationList = recommendationService.getactivityRecommendation(activityId);
        return ResponseEntity.ok(recommendationList);
    }
}
