package com.eduquest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "course_id"})
})
@EntityListeners(AuditingEntityListener.class)
public class Enrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @NotNull
    private Course course;
    
    @Min(0)
    @Max(100)
    private Integer progress = 0; // percentage (0-100)
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CompletionStatus completionStatus = CompletionStatus.IN_PROGRESS;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public Enrollment() {}
    
    public Enrollment(User user, Course course) {
        this.user = user;
        this.course = course;
        this.progress = 0;
        this.completionStatus = CompletionStatus.IN_PROGRESS;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public Integer getProgress() {
        return progress;
    }
    
    public void setProgress(Integer progress) {
        this.progress = progress;
        // Auto-update completion status based on progress
        if (progress != null) {
            if (progress >= 100) {
                this.completionStatus = CompletionStatus.COMPLETED;
            } else if (progress > 0) {
                this.completionStatus = CompletionStatus.IN_PROGRESS;
            } else {
                this.completionStatus = CompletionStatus.NOT_STARTED;
            }
        }
    }
    
    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }
    
    public void setCompletionStatus(CompletionStatus completionStatus) {
        this.completionStatus = completionStatus;
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
    
    // Helper methods
    public void updateProgress(Integer newProgress) {
        setProgress(newProgress);
    }
    
    // Enum for Completion Status
    public enum CompletionStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETED
    }
}








