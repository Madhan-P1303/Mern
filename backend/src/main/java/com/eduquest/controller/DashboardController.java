package com.eduquest.controller;

import com.eduquest.dto.DashboardDto;
import com.eduquest.entity.User;
import com.eduquest.security.UserDetailsImpl;
import com.eduquest.service.DashboardService;
import com.eduquest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<DashboardDto> getDashboard(Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userService.getUserById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            DashboardDto dashboard = dashboardService.getUserDashboard(user);
            return ResponseEntity.ok(dashboard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardDto> getUserDashboard(@PathVariable Long userId) {
        try {
            DashboardDto dashboard = dashboardService.getUserDashboard(userId);
            return ResponseEntity.ok(dashboard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}








