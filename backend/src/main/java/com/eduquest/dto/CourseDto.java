package com.eduquest.dto;

import com.eduquest.entity.Course;
import com.eduquest.entity.User;

import java.time.LocalDateTime;

public class CourseDto {
    
    private Long id;
    private String title;
    private String description;
    private String category;
    private Course.Level level;
    private Long instructorId;
    private String instructorName;
    private Integer duration;
    private Integer lessons;
    private Integer studentsEnrolled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public CourseDto() {}
    
    public CourseDto(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.category = course.getCategory();
        this.level = course.getLevel();
        this.instructorId = course.getInstructor().getId();
        this.instructorName = course.getInstructor().getName();
        this.duration = course.getDuration();
        this.lessons = course.getLessons();
        this.studentsEnrolled = course.getStudentsEnrolled();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
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
    
    public Course.Level getLevel() {
        return level;
    }
    
    public void setLevel(Course.Level level) {
        this.level = level;
    }
    
    public Long getInstructorId() {
        return instructorId;
    }
    
    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
    
    public String getInstructorName() {
        return instructorName;
    }
    
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
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
}








