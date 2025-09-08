package com.eduquest.service;

import com.eduquest.dto.CourseCreateDto;
import com.eduquest.entity.Course;
import com.eduquest.entity.User;
import com.eduquest.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    @Test
    void testCreateCourse() {
        // Create a test instructor
        User instructor = new User();
        instructor.setName("Test Instructor");
        instructor.setEmail("instructor@test.com");
        instructor.setPassword("password123");
        instructor.setRole(User.Role.INSTRUCTOR);
        instructor = userService.updateUser(instructor);

        // Create course DTO
        CourseCreateDto courseDto = new CourseCreateDto();
        courseDto.setTitle("Test Course");
        courseDto.setDescription("Test Description");
        courseDto.setCategory("Test Category");
        courseDto.setLevel(Course.Level.BEGINNER);
        courseDto.setDuration(10);
        courseDto.setLessons(5);

        // Create course
        Course course = courseService.createCourse(courseDto, instructor);

        // Verify course was created
        assertNotNull(course.getId());
        assertEquals("Test Course", course.getTitle());
        assertEquals(instructor.getId(), course.getInstructor().getId());
        assertEquals(0, course.getStudentsEnrolled());
    }

    @Test
    void testGetAllCourses() {
        // Get all courses
        var courses = courseService.getAllCourses();
        
        // Should not be null
        assertNotNull(courses);
    }
}



