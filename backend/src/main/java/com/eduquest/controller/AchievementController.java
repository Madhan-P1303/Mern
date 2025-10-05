package com.eduquest.controller;

import com.eduquest.dto.AchievementDto;
import com.eduquest.entity.Achievement;
import com.eduquest.entity.User;
import com.eduquest.security.UserDetailsImpl;
import com.eduquest.service.AchievementService;
import com.eduquest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/achievements")
public class AchievementController {
    
    @Autowired
    private AchievementService achievementService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<List<AchievementDto>> getMyAchievements(Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userService.getUserById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            List<Achievement> achievements = achievementService.getUserAchievements(user);
            List<AchievementDto> achievementDtos = achievements.stream()
                    .map(AchievementDto::new)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(achievementDtos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AchievementDto>> getUserAchievements(@PathVariable Long userId) {
        try {
            List<Achievement> achievements = achievementService.getUserAchievementsById(userId);
            List<AchievementDto> achievementDtos = achievements.stream()
                    .map(AchievementDto::new)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(achievementDtos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/count")
    @PreAuthorize("hasRole('STUDENT') or hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getMyAchievementCount(Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long count = achievementService.getUserAchievementCount(userDetails.getId());
            
            return ResponseEntity.ok("{\"achievementCount\": " + count + "}");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/types")
    public ResponseEntity<?> getAchievementTypes() {
        Achievement.AchievementType[] types = Achievement.AchievementType.values();
        return ResponseEntity.ok(types);
    }
}








