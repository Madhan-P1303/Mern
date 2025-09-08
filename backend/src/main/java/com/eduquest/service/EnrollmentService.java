package com.eduquest.service;

import com.eduquest.dto.ProgressUpdateDto;
import com.eduquest.entity.Course;
import com.eduquest.entity.Enrollment;
import com.eduquest.entity.User;
import com.eduquest.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AchievementService achievementService;
    
    public Enrollment enrollUserInCourse(Long courseId, User user) {
        // Check if user is a student
        if (user.getRole() != User.Role.STUDENT) {
            throw new RuntimeException("Only students can enroll in courses");
        }
        
        // Get course
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        
        // Check if already enrolled
        if (enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new RuntimeException("User is already enrolled in this course");
        }
        
        // Create enrollment
        Enrollment enrollment = new Enrollment(user, course);
        enrollment = enrollmentRepository.save(enrollment);
        
        // Update course student count
        courseService.incrementStudentsEnrolled(courseId);
        
        // Update user streak
        achievementService.updateUserStreak(user);
        
        return enrollment;
    }
    
    public Enrollment updateProgress(Long courseId, ProgressUpdateDto progressDto, User user) {
        // Get course
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        
        // Get enrollment
        Enrollment enrollment = enrollmentRepository.findByUserAndCourse(user, course)
                .orElseThrow(() -> new RuntimeException("User is not enrolled in this course"));
        
        // Update progress
        enrollment.updateProgress(progressDto.getProgress());
        enrollment = enrollmentRepository.save(enrollment);
        
        // Update user streak
        achievementService.updateUserStreak(user);
        
        // Check for achievements
        if (enrollment.getProgress() == 100) {
            // Course completed
            achievementService.checkAndAwardCourseCompletionAchievement(user, course);
            achievementService.checkAndAwardPerfectScoreAchievement(user, course);
            achievementService.checkAndAwardEarlyBirdAchievement(user, course);
            achievementService.checkAndAwardDedicatedLearnerAchievement(user);
        }
        
        return enrollment;
    }
    
    public List<Enrollment> getUserEnrollments(User user) {
        return enrollmentRepository.findByUser(user);
    }
    
    public List<Enrollment> getUserEnrollmentsById(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }
    
    public List<Enrollment> getCourseEnrollments(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }
    
    public Optional<Enrollment> getEnrollmentByUserAndCourse(User user, Course course) {
        return enrollmentRepository.findByUserAndCourse(user, course);
    }
    
    public Optional<Enrollment> getEnrollmentByUserAndCourseId(User user, Long courseId) {
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        return enrollmentRepository.findByUserAndCourse(user, course);
    }
    
    public boolean isUserEnrolledInCourse(User user, Course course) {
        return enrollmentRepository.existsByUserAndCourse(user, course);
    }
    
    public boolean isUserEnrolledInCourseId(User user, Long courseId) {
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        return enrollmentRepository.existsByUserAndCourse(user, course);
    }
    
    public void unenrollUserFromCourse(Long courseId, User user) {
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        
        Enrollment enrollment = enrollmentRepository.findByUserAndCourse(user, course)
                .orElseThrow(() -> new RuntimeException("User is not enrolled in this course"));
        
        enrollmentRepository.delete(enrollment);
        
        // Update course student count
        courseService.decrementStudentsEnrolled(courseId);
    }
    
    public Long getEnrollmentCountByCourse(Long courseId) {
        return enrollmentRepository.countByCourseId(courseId);
    }
    
    public Long getEnrollmentCountByUser(Long userId) {
        return enrollmentRepository.countByUserId(userId);
    }
    
    public List<Enrollment> getUserEnrollmentsByStatus(Long userId, Enrollment.CompletionStatus status) {
        return enrollmentRepository.findByUserIdAndCompletionStatus(userId, status);
    }
}
