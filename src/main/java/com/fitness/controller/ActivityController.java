package com.fitness.controller;

import com.fitness.dto.ActivityRequest;
import com.fitness.dto.ActivityResponse;
import com.fitness.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
   private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest){
        return ResponseEntity.ok(activityService.trackActivity(activityRequest));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader(value = "X-User-ID") String userId){
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }
}
