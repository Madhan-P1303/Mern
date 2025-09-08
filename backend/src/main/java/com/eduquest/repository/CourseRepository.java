package com.eduquest.repository;

import com.eduquest.entity.Course;
import com.eduquest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    List<Course> findByInstructor(User instructor);
    
    List<Course> findByCategory(String category);
    
    List<Course> findByLevel(Course.Level level);
    
    List<Course> findByTitleContainingIgnoreCase(String title);
    
    @Query("SELECT c FROM Course c WHERE c.instructor.id = :instructorId")
    List<Course> findByInstructorId(@Param("instructorId") Long instructorId);
    
    @Query("SELECT c FROM Course c WHERE c.category = :category AND c.level = :level")
    List<Course> findByCategoryAndLevel(@Param("category") String category, @Param("level") Course.Level level);
    
    @Query("SELECT c FROM Course c ORDER BY c.studentsEnrolled DESC")
    List<Course> findAllOrderByStudentsEnrolledDesc();
    
    @Query("SELECT c FROM Course c ORDER BY c.createdAt DESC")
    List<Course> findAllOrderByCreatedAtDesc();
    
    @Query("SELECT c FROM Course c WHERE c.title LIKE %:searchTerm% OR c.description LIKE %:searchTerm%")
    List<Course> findByTitleOrDescriptionContaining(@Param("searchTerm") String searchTerm);
}



