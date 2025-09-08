package com.eduquest.controller;

import com.eduquest.dto.CourseCreateDto;
import com.eduquest.dto.CourseDto;
import com.eduquest.entity.Course;
import com.eduquest.entity.User;
import com.eduquest.security.UserDetailsImpl;
import com.eduquest.service.CourseService;
import com.eduquest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<CourseDto> courseDtos = courses.stream()
                .map(CourseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseDtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        Optional<Course> courseOpt = courseService.getCourseById(id);
        if (courseOpt.isPresent()) {
            CourseDto courseDto = new CourseDto(courseOpt.get());
            return ResponseEntity.ok(courseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseCreateDto courseDto, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User instructor = userService.getUserById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Course course = courseService.createCourse(courseDto, instructor);
            CourseDto courseResponse = new CourseDto(course);
            return ResponseEntity.ok(courseResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseCreateDto courseDto, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User currentUser = userService.getUserById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Course course = courseService.updateCourse(id, courseDto, currentUser);
            CourseDto courseResponse = new CourseDto(course);
            return ResponseEntity.ok(courseResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User adminUser = userService.getUserById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            courseService.deleteCourse(id, adminUser);
            return ResponseEntity.ok("Course deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<CourseDto>> getCoursesByCategory(@PathVariable String category) {
        List<Course> courses = courseService.getCoursesByCategory(category);
        List<CourseDto> courseDtos = courses.stream()
                .map(CourseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseDtos);
    }
    
    @GetMapping("/level/{level}")
    public ResponseEntity<List<CourseDto>> getCoursesByLevel(@PathVariable Course.Level level) {
        List<Course> courses = courseService.getCoursesByLevel(level);
        List<CourseDto> courseDtos = courses.stream()
                .map(CourseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseDtos);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<CourseDto>> searchCourses(@RequestParam String q) {
        List<Course> courses = courseService.searchCourses(q);
        List<CourseDto> courseDtos = courses.stream()
                .map(CourseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseDtos);
    }
    
    @GetMapping("/popular")
    public ResponseEntity<List<CourseDto>> getPopularCourses() {
        List<Course> courses = courseService.getPopularCourses();
        List<CourseDto> courseDtos = courses.stream()
                .map(CourseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseDtos);
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<CourseDto>> getRecentCourses() {
        List<Course> courses = courseService.getRecentCourses();
        List<CourseDto> courseDtos = courses.stream()
                .map(CourseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseDtos);
    }
}
