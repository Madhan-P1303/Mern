package com.eduquest.service;

import com.eduquest.entity.Achievement;
import com.eduquest.entity.Course;
import com.eduquest.entity.User;
import com.eduquest.repository.AchievementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AchievementServiceTest {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Test
    void testAwardAchievement() {
        // Create a test user
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(User.Role.STUDENT);
        user = userService.updateUser(user);

        // Award achievement
        Achievement achievement = achievementService.awardAchievement(
            user, 
            "Test Achievement", 
            "Test Description", 
            Achievement.AchievementType.FIRST_COURSE
        );

        // Verify achievement was created
        assertNotNull(achievement.getId());
        assertEquals("Test Achievement", achievement.getTitle());
        assertEquals(user.getId(), achievement.getUser().getId());
    }

    @Test
    void testGetUserAchievements() {
        // Create a test user
        User user = new User();
        user.setName("Test User");
        user.setEmail("test2@example.com");
        user.setPassword("password123");
        user.setRole(User.Role.STUDENT);
        user = userService.updateUser(user);

        // Award an achievement
        achievementService.awardAchievement(
            user, 
            "Test Achievement", 
            "Test Description", 
            Achievement.AchievementType.FIRST_COURSE
        );

        // Get user achievements
        var achievements = achievementService.getUserAchievements(user);
        
        // Should have 1 achievement
        assertEquals(1, achievements.size());
        assertEquals("Test Achievement", achievements.get(0).getTitle());
    }
}








