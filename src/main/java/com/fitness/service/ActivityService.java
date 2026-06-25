package com.fitness.service;

import com.fitness.dto.ActivityRequest;
import com.fitness.dto.ActivityResponse;
import com.fitness.model.Activity;
import com.fitness.model.User;
import com.fitness.repository.ActivityRepository;
import com.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {
        User user = userRepository.findById(activityRequest.getUserId()).orElseThrow(()-> new RuntimeException("Invalid User : "+activityRequest.getUserId()));;
        Activity activity = Activity.builder()
                .user(user)
                .type(activityRequest.getType())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .startTime(activityRequest.getStartTime())
                .duration(activityRequest.getDuration())
                .build();

      Activity savedActivity = activityRepository.save(activity);
      
      return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity savedActivity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(savedActivity.getId());
        response.setType(savedActivity.getType());
        response.setDuration(savedActivity.getDuration());
        response.setStartTime(savedActivity.getStartTime());
        response.setCaloriesBurned(savedActivity.getCaloriesBurned());
        response.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
        response.setCreatedAt(savedActivity.getCreatedAt());
        response.setUpdatedAt(savedActivity.getUpdatedAt());
        response.setUserId(savedActivity.getUser().getId());

        return response;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activityList = activityRepository.findByUserId(userId);
        return activityList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
