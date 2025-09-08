package com.eduquest.repository;

import com.eduquest.entity.Achievement;
import com.eduquest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    
    List<Achievement> findByUser(User user);
    
    List<Achievement> findByUserOrderByEarnedDateDesc(User user);
    
    @Query("SELECT a FROM Achievement a WHERE a.user.id = :userId")
    List<Achievement> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT a FROM Achievement a WHERE a.user.id = :userId ORDER BY a.earnedDate DESC")
    List<Achievement> findByUserIdOrderByEarnedDateDesc(@Param("userId") Long userId);
    
    @Query("SELECT a FROM Achievement a WHERE a.user.id = :userId AND a.type = :type")
    List<Achievement> findByUserIdAndType(@Param("userId") Long userId, @Param("type") Achievement.AchievementType type);
    
    @Query("SELECT COUNT(a) FROM Achievement a WHERE a.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(a) FROM Achievement a WHERE a.user.id = :userId AND a.type = :type")
    Long countByUserIdAndType(@Param("userId") Long userId, @Param("type") Achievement.AchievementType type);
    
    @Query("SELECT a FROM Achievement a WHERE a.user.id = :userId AND a.type = :type")
    Optional<Achievement> findFirstByUserIdAndType(@Param("userId") Long userId, @Param("type") Achievement.AchievementType type);
    
    @Query("SELECT a FROM Achievement a WHERE a.user.id = :userId AND a.earnedDate >= :startDate")
    List<Achievement> findByUserIdAndEarnedDateAfter(@Param("userId") Long userId, @Param("startDate") java.time.LocalDateTime startDate);
}



