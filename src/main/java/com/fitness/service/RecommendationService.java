package com.fitness.service;

import com.fitness.dto.RecommendationRequest;
import com.fitness.model.Activity;
import com.fitness.model.Recommendation;
import com.fitness.model.User;
import com.fitness.repository.ActivityRepository;
import com.fitness.repository.RecommendationRepository;
import com.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final RecommendationRepository recommendationRepository;

    public Recommendation generateRecommendation(RecommendationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new RuntimeException("USER NOT FOUND" + request.getUserId()));

        Activity activity = activityRepository.findById(request.getActivityId())
                .orElseThrow(()->new RuntimeException("ACTIVITY NOT FOUND" + request.getActivityId()));

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .activity(activity)
                .improvements(request.getImprovements())
                .safety(request.getSafety())
                .suggestions(request.getSuggestions())
                .build();

        return recommendationRepository.save(recommendation);
    }

    public List<Recommendation> getUserRecommendation(String userId){
        return recommendationRepository.findByUserId(userId);
    }

    public List<Recommendation> getActivityRecommendation(String activityId){
        return recommendationRepository.findByActivityId(activityId);
    }
}
