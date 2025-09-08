package com.eduquest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@EntityListeners(AuditingEntityListener.class)
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 200)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotBlank
    @Size(max = 100)
    private String category;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Level level = Level.BEGINNER;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    @NotNull
    private User instructor;
    
    @Positive
    private Integer duration; // in hours
    
    @Positive
    private Integer lessons;
    
    @Column(name = "students_enrolled")
    private Integer studentsEnrolled = 0;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();
    
    // Constructors
    public Course() {}
    
    public Course(String title, String description, String category, Level level, User instructor, Integer duration, Integer lessons) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.instructor = instructor;
        this.duration = duration;
        this.lessons = lessons;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Level getLevel() {
        return level;
    }
    
    public void setLevel(Level level) {
        this.level = level;
    }
    
    public User getInstructor() {
        return instructor;
    }
    
    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public Integer getLessons() {
        return lessons;
    }
    
    public void setLessons(Integer lessons) {
        this.lessons = lessons;
    }
    
    public Integer getStudentsEnrolled() {
        return studentsEnrolled;
    }
    
    public void setStudentsEnrolled(Integer studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
    
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
    
    // Helper methods
    public void incrementStudentsEnrolled() {
        this.studentsEnrolled = (this.studentsEnrolled == null) ? 1 : this.studentsEnrolled + 1;
    }
    
    public void decrementStudentsEnrolled() {
        if (this.studentsEnrolled != null && this.studentsEnrolled > 0) {
            this.studentsEnrolled--;
        }
    }
    
    // Enum for Course Level
    public enum Level {
        BEGINNER, INTERMEDIATE, ADVANCED
    }
}
