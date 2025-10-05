package com.eduquest.service;

import com.eduquest.dto.AchievementDto;
import com.eduquest.dto.DashboardDto;
import com.eduquest.dto.EnrollmentDto;
import com.eduquest.entity.Achievement;
import com.eduquest.entity.Enrollment;
import com.eduquest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DashboardService {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @Autowired
    private AchievementService achievementService;
    
    public DashboardDto getUserDashboard(Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        DashboardDto dashboard = new DashboardDto(userId, user.getName());
        
        // Get user enrollments
        List<Enrollment> enrollments = enrollmentService.getUserEnrollmentsById(userId);
        
        // Calculate statistics
        int totalEnrolled = enrollments.size();
        int completed = (int) enrollments.stream()
                .filter(e -> e.getCompletionStatus() == Enrollment.CompletionStatus.COMPLETED)
                .count();
        int inProgress = (int) enrollments.stream()
                .filter(e -> e.getCompletionStatus() == Enrollment.CompletionStatus.IN_PROGRESS)
                .count();
        
        // Calculate total hours (sum of completed course durations)
        int totalHours = enrollments.stream()
                .filter(e -> e.getCompletionStatus() == Enrollment.CompletionStatus.COMPLETED)
                .mapToInt(e -> e.getCourse().getDuration() != null ? e.getCourse().getDuration() : 0)
                .sum();
        
        // Get current streak
        Integer currentStreak = user.getCurrentStreak() != null ? user.getCurrentStreak() : 0;
        
        // Get total achievements
        Long totalAchievements = achievementService.getUserAchievementCount(userId);
        
        // Get recent enrollments (last 5)
        List<Enrollment> recentEnrollments = enrollments.stream()
                .sorted((e1, e2) -> e2.getCreatedAt().compareTo(e1.getCreatedAt()))
                .limit(5)
                .collect(Collectors.toList());
        
        // Get recent achievements (last 5)
        List<Achievement> recentAchievements = achievementService.getUserAchievementsById(userId).stream()
                .limit(5)
                .collect(Collectors.toList());
        
        // Set dashboard data
        dashboard.setTotalEnrolledCourses(totalEnrolled);
        dashboard.setCompletedCourses(completed);
        dashboard.setInProgressCourses(inProgress);
        dashboard.setTotalHours(totalHours);
        dashboard.setCurrentStreak(currentStreak);
        dashboard.setTotalAchievements(totalAchievements.intValue());
        dashboard.setRecentEnrollments(recentEnrollments.stream()
                .map(EnrollmentDto::new)
                .collect(Collectors.toList()));
        dashboard.setRecentAchievements(recentAchievements.stream()
                .map(AchievementDto::new)
                .collect(Collectors.toList()));
        dashboard.setLastActivityDate(user.getLastActivityDate());
        
        return dashboard;
    }
    
    public DashboardDto getUserDashboard(User user) {
        return getUserDashboard(user.getId());
    }
}








