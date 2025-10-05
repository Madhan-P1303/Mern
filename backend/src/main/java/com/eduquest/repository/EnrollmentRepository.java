package com.eduquest.repository;

import com.eduquest.entity.Course;
import com.eduquest.entity.Enrollment;
import com.eduquest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    List<Enrollment> findByUser(User user);
    
    List<Enrollment> findByCourse(Course course);
    
    Optional<Enrollment> findByUserAndCourse(User user, Course course);
    
    boolean existsByUserAndCourse(User user, Course course);
    
    @Query("SELECT e FROM Enrollment e WHERE e.user.id = :userId")
    List<Enrollment> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId")
    List<Enrollment> findByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.user.id = :userId AND e.completionStatus = :status")
    List<Enrollment> findByUserIdAndCompletionStatus(@Param("userId") Long userId, @Param("status") Enrollment.CompletionStatus status);
    
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId")
    Long countByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.user.id = :userId ORDER BY e.createdAt DESC")
    List<Enrollment> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
}








