package com.eduquest.dto;

import com.eduquest.entity.Enrollment;

import java.time.LocalDateTime;

public class EnrollmentDto {
    
    private Long id;
    private Long userId;
    private String userName;
    private Long courseId;
    private String courseTitle;
    private Integer progress;
    private Enrollment.CompletionStatus completionStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public EnrollmentDto() {}
    
    public EnrollmentDto(Enrollment enrollment) {
        this.id = enrollment.getId();
        this.userId = enrollment.getUser().getId();
        this.userName = enrollment.getUser().getName();
        this.courseId = enrollment.getCourse().getId();
        this.courseTitle = enrollment.getCourse().getTitle();
        this.progress = enrollment.getProgress();
        this.completionStatus = enrollment.getCompletionStatus();
        this.createdAt = enrollment.getCreatedAt();
        this.updatedAt = enrollment.getUpdatedAt();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Long getCourseId() {
        return courseId;
    }
    
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseTitle() {
        return courseTitle;
    }
    
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
    
    public Integer getProgress() {
        return progress;
    }
    
    public void setProgress(Integer progress) {
        this.progress = progress;
    }
    
    public Enrollment.CompletionStatus getCompletionStatus() {
        return completionStatus;
    }
    
    public void setCompletionStatus(Enrollment.CompletionStatus completionStatus) {
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
}



