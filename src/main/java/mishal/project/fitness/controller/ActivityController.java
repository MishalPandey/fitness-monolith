package mishal.project.fitness.controller;

import lombok.RequiredArgsConstructor;
import mishal.project.fitness.dto.ActivityRequest;
import mishal.project.fitness.dto.ActivityResponse;
import mishal.project.fitness.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request)
    {
        return ResponseEntity.ok(activityService.trackActivity(request));
    }
    //yaha headers ko user kar rha euserId pass karne ke liye @Request Params ko generally
    // pagination/query filteration values ke liye use karte kyukihame url clean rakhna hota
    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(
            @RequestHeader(value = "X-User-ID") String usersId)
    {
        return ResponseEntity.ok(activityService.getUserActivities(usersId));
    }


}
