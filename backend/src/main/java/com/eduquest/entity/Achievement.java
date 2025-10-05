package com.eduquest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "achievements")
@EntityListeners(AuditingEntityListener.class)
public class Achievement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;
    
    @NotBlank
    @Size(max = 200)
    private String title;
    
    @Size(max = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private AchievementType type;
    
    @Column(name = "earned_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime earnedDate;
    
    // Constructors
    public Achievement() {}
    
    public Achievement(User user, String title, String description, AchievementType type) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.type = type;
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
    
    public AchievementType getType() {
        return type;
    }
    
    public void setType(AchievementType type) {
        this.type = type;
    }
    
    public LocalDateTime getEarnedDate() {
        return earnedDate;
    }
    
    public void setEarnedDate(LocalDateTime earnedDate) {
        this.earnedDate = earnedDate;
    }
    
    // Enum for Achievement Types
    public enum AchievementType {
        COURSE_COMPLETION,
        STREAK_3_DAYS,
        STREAK_7_DAYS,
        STREAK_30_DAYS,
        FIRST_COURSE,
        PERFECT_SCORE,
        EARLY_BIRD,
        DEDICATED_LEARNER,
        COURSE_CREATOR,
        MENTOR
    }
}








