package com.eduquest.dto;

import com.eduquest.entity.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CourseCreateDto {
    
    @NotBlank
    @Size(max = 200)
    private String title;
    
    private String description;
    
    @NotBlank
    @Size(max = 100)
    private String category;
    
    @NotNull
    private Course.Level level;
    
    @NotNull
    @Positive
    private Integer duration;
    
    @NotNull
    @Positive
    private Integer lessons;
    
    // Constructors
    public CourseCreateDto() {}
    
    public CourseCreateDto(String title, String description, String category, Course.Level level, Integer duration, Integer lessons) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.duration = duration;
        this.lessons = lessons;
    }
    
    // Getters and Setters
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
}








