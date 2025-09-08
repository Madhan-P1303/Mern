package com.eduquest.service;

import com.eduquest.dto.CourseCreateDto;
import com.eduquest.entity.Course;
import com.eduquest.entity.User;
import com.eduquest.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AchievementService achievementService;
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
    
    public Course createCourse(CourseCreateDto courseDto, User instructor) {
        // Verify instructor role
        if (instructor.getRole() != User.Role.INSTRUCTOR && instructor.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Only instructors can create courses");
        }
        
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setCategory(courseDto.getCategory());
        course.setLevel(courseDto.getLevel());
        course.setInstructor(instructor);
        course.setDuration(courseDto.getDuration());
        course.setLessons(courseDto.getLessons());
        course.setStudentsEnrolled(0);
        
        course = courseRepository.save(course);
        
        // Award course creator achievement
        achievementService.checkAndAwardCourseCreatorAchievement(instructor);
        achievementService.checkAndAwardMentorAchievement(instructor);
        
        return course;
    }
    
    public Course updateCourse(Long id, CourseCreateDto courseDto, User currentUser) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        // Check if user is the instructor or admin
        if (!course.getInstructor().getId().equals(currentUser.getId()) && currentUser.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Only the course instructor or admin can update this course");
        }
        
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setCategory(courseDto.getCategory());
        course.setLevel(courseDto.getLevel());
        course.setDuration(courseDto.getDuration());
        course.setLessons(courseDto.getLessons());
        
        return courseRepository.save(course);
    }
    
    public void deleteCourse(Long id, User currentUser) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        // Only admin can delete courses
        if (currentUser != null && currentUser.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Only admin can delete courses");
        }
        
        courseRepository.delete(course);
    }
    
    public List<Course> getCoursesByInstructor(User instructor) {
        return courseRepository.findByInstructor(instructor);
    }
    
    public List<Course> getCoursesByCategory(String category) {
        return courseRepository.findByCategory(category);
    }
    
    public List<Course> getCoursesByLevel(Course.Level level) {
        return courseRepository.findByLevel(level);
    }
    
    public List<Course> searchCourses(String searchTerm) {
        return courseRepository.findByTitleOrDescriptionContaining(searchTerm);
    }
    
    public List<Course> getPopularCourses() {
        return courseRepository.findAllOrderByStudentsEnrolledDesc();
    }
    
    public List<Course> getRecentCourses() {
        return courseRepository.findAllOrderByCreatedAtDesc();
    }
    
    public void incrementStudentsEnrolled(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        course.incrementStudentsEnrolled();
        courseRepository.save(course);
    }
    
    public void decrementStudentsEnrolled(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        course.decrementStudentsEnrolled();
        courseRepository.save(course);
    }
}
