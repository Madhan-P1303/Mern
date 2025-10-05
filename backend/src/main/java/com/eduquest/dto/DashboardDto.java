package com.eduquest.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DashboardDto {
    
    private Long userId;
    private String userName;
    private Integer totalEnrolledCourses;
    private Integer completedCourses;
    private Integer inProgressCourses;
    private Integer totalHours;
    private Integer currentStreak;
    private Integer totalAchievements;
    private List<EnrollmentDto> recentEnrollments;
    private List<AchievementDto> recentAchievements;
    private LocalDateTime lastActivityDate;
    
    // Constructors
    public DashboardDto() {}
    
    public DashboardDto(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
    
    // Getters and Setters
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
    
    public Integer getTotalEnrolledCourses() {
        return totalEnrolledCourses;
    }
    
    public void setTotalEnrolledCourses(Integer totalEnrolledCourses) {
        this.totalEnrolledCourses = totalEnrolledCourses;
    }
    
    public Integer getCompletedCourses() {
        return completedCourses;
    }
    
    public void setCompletedCourses(Integer completedCourses) {
        this.completedCourses = completedCourses;
    }
    
    public Integer getInProgressCourses() {
        return inProgressCourses;
    }
    
    public void setInProgressCourses(Integer inProgressCourses) {
        this.inProgressCourses = inProgressCourses;
    }
    
    public Integer getTotalHours() {
        return totalHours;
    }
    
    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }
    
    public Integer getCurrentStreak() {
        return currentStreak;
    }
    
    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }
    
    public Integer getTotalAchievements() {
        return totalAchievements;
    }
    
    public void setTotalAchievements(Integer totalAchievements) {
        this.totalAchievements = totalAchievements;
    }
    
    public List<EnrollmentDto> getRecentEnrollments() {
        return recentEnrollments;
    }
    
    public void setRecentEnrollments(List<EnrollmentDto> recentEnrollments) {
        this.recentEnrollments = recentEnrollments;
    }
    
    public List<AchievementDto> getRecentAchievements() {
        return recentAchievements;
    }
    
    public void setRecentAchievements(List<AchievementDto> recentAchievements) {
        this.recentAchievements = recentAchievements;
    }
    
    public LocalDateTime getLastActivityDate() {
        return lastActivityDate;
    }
    
    public void setLastActivityDate(LocalDateTime lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }
}








