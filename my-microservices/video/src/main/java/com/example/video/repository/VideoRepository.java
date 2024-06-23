package com.example.video.repository;

import com.example.video.domain.Video;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, String> {
    Optional<Video> findByUserId(String userId);

    // Custom query to delete videos by user ID
    @Modifying
    @Transactional
    @Query("DELETE FROM Video v WHERE v.userId = :userId")
    void deleteByUserId(String userId);

}
