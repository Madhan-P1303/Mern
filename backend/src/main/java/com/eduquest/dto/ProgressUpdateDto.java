package com.eduquest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProgressUpdateDto {
    
    @NotNull
    @Min(0)
    @Max(100)
    private Integer progress;
    
    // Constructors
    public ProgressUpdateDto() {}
    
    public ProgressUpdateDto(Integer progress) {
        this.progress = progress;
    }
    
    // Getters and Setters
    public Integer getProgress() {
        return progress;
    }
    
    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}








