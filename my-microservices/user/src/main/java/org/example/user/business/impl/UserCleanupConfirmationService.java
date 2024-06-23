package org.example.user.business.impl;

import jakarta.transaction.Transactional;
import org.example.user.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserCleanupConfirmationService {
    private static final Logger log = LoggerFactory.getLogger(UserCleanupConfirmationService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserCleanupConfirmationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @RabbitListener(queues = "user_cleanup_completed_queue")
    public void handleCleanupCompleted(String userId) {
        log.info("Attempting to delete user with ID: {}", userId);
        userId = userId.replace("\"", "");
        userRepository.deleteByUserId(userId.replace("\"", ""));
        log.info("User deleted successfully with ID: {}", userId);
    }
}
