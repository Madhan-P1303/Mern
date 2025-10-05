package com.eduquest.dto;

import com.eduquest.entity.Achievement;

import java.time.LocalDateTime;

public class AchievementDto {
    
    private Long id;
    private Long userId;
    private String userName;
    private String title;
    private String description;
    private Achievement.AchievementType type;
    private LocalDateTime earnedDate;
    
    // Constructors
    public AchievementDto() {}
    
    public AchievementDto(Achievement achievement) {
        this.id = achievement.getId();
        this.userId = achievement.getUser().getId();
        this.userName = achievement.getUser().getName();
        this.title = achievement.getTitle();
        this.description = achievement.getDescription();
        this.type = achievement.getType();
        this.earnedDate = achievement.getEarnedDate();
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
    
    public Achievement.AchievementType getType() {
        return type;
    }
    
    public void setType(Achievement.AchievementType type) {
        this.type = type;
    }
    
    public LocalDateTime getEarnedDate() {
        return earnedDate;
    }
    
    public void setEarnedDate(LocalDateTime earnedDate) {
        this.earnedDate = earnedDate;
    }
}








