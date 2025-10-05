package com.eduquest.service;

import com.eduquest.entity.Achievement;
import com.eduquest.entity.Course;
import com.eduquest.entity.Enrollment;
import com.eduquest.entity.User;
import com.eduquest.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AchievementService {
    
    @Autowired
    private AchievementRepository achievementRepository;
    
    @Autowired
    private UserService userService;
    
    public List<Achievement> getUserAchievements(User user) {
        return achievementRepository.findByUserOrderByEarnedDateDesc(user);
    }
    
    public List<Achievement> getUserAchievementsById(Long userId) {
        return achievementRepository.findByUserIdOrderByEarnedDateDesc(userId);
    }
    
    public Long getUserAchievementCount(Long userId) {
        return achievementRepository.countByUserId(userId);
    }
    
    public Achievement awardAchievement(User user, String title, String description, Achievement.AchievementType type) {
        // Check if user already has this achievement
        Optional<Achievement> existingAchievement = achievementRepository.findFirstByUserIdAndType(user.getId(), type);
        if (existingAchievement.isPresent()) {
            return existingAchievement.get(); // Return existing achievement
        }
        
        // Create new achievement
        Achievement achievement = new Achievement(user, title, description, type);
        return achievementRepository.save(achievement);
    }
    
    public void checkAndAwardCourseCompletionAchievement(User user, Course course) {
        // Award course completion achievement
        String title = "Course Completed: " + course.getTitle();
        String description = "Congratulations! You have successfully completed the course: " + course.getTitle();
        awardAchievement(user, title, description, Achievement.AchievementType.COURSE_COMPLETION);
        
        // Check for first course achievement
        Long courseCompletionCount = achievementRepository.countByUserIdAndType(user.getId(), Achievement.AchievementType.COURSE_COMPLETION);
        if (courseCompletionCount == 1) {
            awardAchievement(user, "First Course Completed", "You completed your first course! Keep up the great work!", Achievement.AchievementType.FIRST_COURSE);
        }
    }
    
    public void checkAndAwardStreakAchievements(User user) {
        Integer currentStreak = user.getCurrentStreak();
        
        if (currentStreak != null) {
            if (currentStreak >= 3 && !hasAchievement(user, Achievement.AchievementType.STREAK_3_DAYS)) {
                awardAchievement(user, "3-Day Streak", "You've maintained a 3-day learning streak! Great dedication!", Achievement.AchievementType.STREAK_3_DAYS);
            }
            
            if (currentStreak >= 7 && !hasAchievement(user, Achievement.AchievementType.STREAK_7_DAYS)) {
                awardAchievement(user, "7-Day Streak", "Amazing! You've maintained a 7-day learning streak!", Achievement.AchievementType.STREAK_7_DAYS);
            }
            
            if (currentStreak >= 30 && !hasAchievement(user, Achievement.AchievementType.STREAK_30_DAYS)) {
                awardAchievement(user, "30-Day Streak", "Incredible dedication! You've maintained a 30-day learning streak!", Achievement.AchievementType.STREAK_30_DAYS);
            }
        }
    }
    
    public void checkAndAwardPerfectScoreAchievement(User user, Course course) {
        // This would be called when a user gets 100% on a course
        String title = "Perfect Score: " + course.getTitle();
        String description = "Outstanding! You achieved a perfect score in " + course.getTitle();
        awardAchievement(user, title, description, Achievement.AchievementType.PERFECT_SCORE);
    }
    
    public void checkAndAwardEarlyBirdAchievement(User user, Course course) {
        // Award early bird achievement if user completes course within first week
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        if (course.getCreatedAt().isAfter(oneWeekAgo)) {
            String title = "Early Bird: " + course.getTitle();
            String description = "You were one of the first to complete this course!";
            awardAchievement(user, title, description, Achievement.AchievementType.EARLY_BIRD);
        }
    }
    
    public void checkAndAwardDedicatedLearnerAchievement(User user) {
        // Award if user has completed 5 or more courses
        Long completedCourses = achievementRepository.countByUserIdAndType(user.getId(), Achievement.AchievementType.COURSE_COMPLETION);
        if (completedCourses >= 5 && !hasAchievement(user, Achievement.AchievementType.DEDICATED_LEARNER)) {
            awardAchievement(user, "Dedicated Learner", "You've completed 5 courses! Your dedication to learning is inspiring!", Achievement.AchievementType.DEDICATED_LEARNER);
        }
    }
    
    public void checkAndAwardCourseCreatorAchievement(User user) {
        // Award if user is an instructor and has created courses
        if (user.getRole() == User.Role.INSTRUCTOR) {
            // This would need to be implemented based on course creation count
            // For now, we'll award it if user is an instructor
            if (!hasAchievement(user, Achievement.AchievementType.COURSE_CREATOR)) {
                awardAchievement(user, "Course Creator", "You've created educational content for others to learn from!", Achievement.AchievementType.COURSE_CREATOR);
            }
        }
    }
    
    public void checkAndAwardMentorAchievement(User user) {
        // Award if user has helped other students (this would need additional logic)
        // For now, we'll award it to instructors
        if (user.getRole() == User.Role.INSTRUCTOR && !hasAchievement(user, Achievement.AchievementType.MENTOR)) {
            awardAchievement(user, "Mentor", "You're helping others learn and grow through your teaching!", Achievement.AchievementType.MENTOR);
        }
    }
    
    private boolean hasAchievement(User user, Achievement.AchievementType type) {
        return achievementRepository.findFirstByUserIdAndType(user.getId(), type).isPresent();
    }
    
    public void updateUserStreak(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastActivity = user.getLastActivityDate();
        
        if (lastActivity == null) {
            // First activity
            user.setCurrentStreak(1);
            user.setLastActivityDate(now);
        } else {
            LocalDateTime yesterday = now.minusDays(1);
            LocalDateTime twoDaysAgo = now.minusDays(2);
            
            if (lastActivity.toLocalDate().equals(now.toLocalDate())) {
                // Same day, no change to streak
                return;
            } else if (lastActivity.toLocalDate().equals(yesterday.toLocalDate())) {
                // Consecutive day, increment streak
                user.setCurrentStreak(user.getCurrentStreak() + 1);
            } else if (lastActivity.toLocalDate().isBefore(twoDaysAgo.toLocalDate())) {
                // Streak broken, reset to 1
                user.setCurrentStreak(1);
            }
            
            user.setLastActivityDate(now);
        }
        
        userService.updateUser(user);
        
        // Check for streak achievements
        checkAndAwardStreakAchievements(user);
    }
}








