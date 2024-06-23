package org.example.user.repository;

import jakarta.transaction.Transactional;
import org.example.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.userId LIKE :userId")
    void deleteByUserId(String userId);
}
