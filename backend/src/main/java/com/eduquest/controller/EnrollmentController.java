package com.eduquest.controller;

import com.eduquest.dto.EnrollmentDto;
import com.eduquest.dto.ProgressUpdateDto;
import com.eduquest.entity.Enrollment;
import com.eduquest.entity.User;
import com.eduquest.security.UserDetailsImpl;
import com.eduquest.service.EnrollmentService;
import com.eduquest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/enroll")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> enrollInCourse(@PathVariable Long courseId, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userService.getUserById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Enrollment enrollment = enrollmentService.enrollUserInCourse(courseId, user);
            EnrollmentDto enrollmentDto = new EnrollmentDto(enrollment);
            return ResponseEntity.ok(enrollmentDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{courseId}/progress")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> updateProgress(@PathVariable Long courseId, @Valid @RequestBody ProgressUpdateDto progressDto, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userService.getUserById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Enrollment enrollment = enrollmentService.updateProgress(courseId, progressDto, user);
            EnrollmentDto enrollmentDto = new EnrollmentDto(enrollment);
            return ResponseEntity.ok(enrollmentDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/my-courses")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<EnrollmentDto>> getMyCourses(Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userService.getUserById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            List<Enrollment> enrollments = enrollmentService.getUserEnrollments(user);
            List<EnrollmentDto> enrollmentDtos = enrollments.stream()
                    .map(EnrollmentDto::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(enrollmentDtos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/my-courses/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> getMyCourseEnrollment(@PathVariable Long courseId, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userService.getUserById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            var enrollmentOpt = enrollmentService.getEnrollmentByUserAndCourseId(user, courseId);
            if (enrollmentOpt.isPresent()) {
                EnrollmentDto enrollmentDto = new EnrollmentDto(enrollmentOpt.get());
                return ResponseEntity.ok(enrollmentDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> unenrollFromCourse(@PathVariable Long courseId, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userService.getUserById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            enrollmentService.unenrollUserFromCourse(courseId, user);
            return ResponseEntity.ok("Successfully unenrolled from course");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<List<EnrollmentDto>> getCourseEnrollments(@PathVariable Long courseId) {
        try {
            List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseId);
            List<EnrollmentDto> enrollmentDtos = enrollments.stream()
                    .map(EnrollmentDto::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(enrollmentDtos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/stats/{courseId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getCourseEnrollmentStats(@PathVariable Long courseId) {
        try {
            Long enrollmentCount = enrollmentService.getEnrollmentCountByCourse(courseId);
            return ResponseEntity.ok("{\"enrollmentCount\": " + enrollmentCount + "}");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}



